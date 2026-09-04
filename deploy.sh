#!/usr/bin/env bash
###############################################################################
# jshERP（管伊佳ERP）一键部署脚本
#
# 适用系统: Ubuntu / Debian / CentOS / Rocky / AlmaLinux（x86_64 / aarch64）
#
# 脚本会依次完成:
#   1. 检测并安装基础环境(JDK8、Maven、Node、MySQL/MariaDB、Redis、Nginx)
#   2. 初始化数据库 jsh_erp 并导入后端 docs/jsh_erp.sql
#   3. 编译后端 jshERP-boot -> jshERP.jar，编译前端 jshERP-web -> dist
#      (低内存服务器会自动补建 swapfile, 防止前端构建被系统 OOM 杀死)
#   4. 组装运行目录(默认 /opt/jshERP)，生成并覆盖后端 application.yml 配置
#   5. 生成 Nginx 站点配置(默认监听 3000，反代 /jshERP-boot -> 127.0.0.1:9999)
#   6. 启动后端与 Nginx，自检后输出访问地址和默认账号
#
# 用法:
#   sudo bash deploy.sh                # 全流程一键部署
#   sudo bash deploy.sh --skip-deps    # 跳过依赖安装(服务器已装好环境时使用)
#   sudo bash deploy.sh --skip-build   # 跳过前后端编译(已有构建产物时使用)
#
# 常用可覆盖的环境变量(默认值见下方 CONFIG):
#   INSTALL_DIR      安装根目录             默认 /opt/jshERP
#   HTTP_PORT        Web(nginx)监听端口     默认 3000
#   BACKEND_PORT     后端端口               默认 9999(与源码 application.yml 一致)
#   SERVER_NAME      nginx server_name      默认 localhost
#   DB_NAME          数据库名               默认 jsh_erp
#   DB_USER          数据库应用账号         默认 jsh_erp
#   DB_PASSWORD      数据库应用账号密码     默认 jshErp@123
#   MYSQL_ROOT_PASSWORD 服务器 MySQL root 密码(留空则用系统 root/socket 方式)
#   REDIS_PASSWORD   Redis 密码            默认 1234abcd
#   NPM_REGISTRY     npm 镜像源            默认 https://registry.npmmirror.com
#   MAVEN_MIRROR     Maven 镜像源(留空禁用) 默认 https://maven.aliyun.com/repository/public
#   AUTO_START       是否写开机自启(rc.local) 默认 1
#   JSH_SWAP_TARGET_MB  构建期目标内存(内存+swap, MB; 不足自动补建 swap) 默认 4096
#
# 部署完成后默认登录: 租户 jsh / 超管 admin，密码均为 123456
###############################################################################

# ---------------------------- 基础环境定义 ----------------------------
set -uo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BACKEND_SRC="${SCRIPT_DIR}/jshERP-boot"
WEB_SRC="${SCRIPT_DIR}/jshERP-web"
SQL_FILE="${BACKEND_SRC}/docs/jsh_erp.sql"

# 部署配置(可用环境变量覆盖)
INSTALL_DIR="${INSTALL_DIR:-/opt/jshERP}"
BACKEND_HOME="${INSTALL_DIR}/jshERP-boot"
WEB_HOME="${INSTALL_DIR}/jshERP-web"
UPLOAD_DIR="${INSTALL_DIR}/upload"
EXPORT_DIR="${INSTALL_DIR}/export"
HTTP_PORT="${HTTP_PORT:-3000}"
BACKEND_PORT="${BACKEND_PORT:-9999}"
SERVER_NAME="${SERVER_NAME:-localhost}"

DB_NAME="${DB_NAME:-jsh_erp}"
DB_HOST="${DB_HOST:-127.0.0.1}"
DB_PORT="${DB_PORT:-3306}"
DB_USER="${DB_USER:-jsh_erp}"
DB_PASSWORD="${DB_PASSWORD:-jshErp@123}"
MYSQL_ROOT_PASSWORD="${MYSQL_ROOT_PASSWORD:-}"
REDIS_PASSWORD="${REDIS_PASSWORD:-1234abcd}"
NPM_REGISTRY="${NPM_REGISTRY:-https://registry.npmmirror.com}"
MAVEN_MIRROR="${MAVEN_MIRROR:-https://maven.aliyun.com/repository/public}"
AUTO_START="${AUTO_START:-1}"

DO_DEPS=1
DO_BUILD=1

# ---------------------------- 工具函数 ----------------------------
C_RED=$'\033[31m'; C_GRN=$'\033[32m'; C_YEL=$'\033[33m'; C_BLU=$'\033[36m'; C_END=$'\033[0m'

log()  { echo -e "${C_BLU}[jshERP]${C_END} $*"; }
ok()   { echo -e "${C_GRN}[ OK ]${C_END} $*"; }
warn() { echo -e "${C_YEL}[WARN]${C_END} $*"; }
die()  { echo -e "${C_RED}[FAIL]${C_END} $*" >&2; exit 1; }
hr()   { echo -e "${C_BLU}-------------------------------------------------------------------------${C_END}"; }

# 兼容精简 PATH 环境(非登录 shell / su 切换 / 部分 sudo 配置): 
# CentOS/RHEL 系的 mysqld、mariadbd、nginx 等位于 /usr/sbin, 缺目录会导致"明明已安装却被判定缺失"
export PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin${PATH:+:$PATH}"

cmd_exists() { command -v "$1" >/dev/null 2>&1; }

# apt 系: 判断软件源中某包是否存在可安装候选。
# 用 LC_ALL=C 规避中英文 locale 差异; 虚拟包/缺失包均无候选(如 Debian 的 mysql-server 为虚拟包)
apt_has_candidate() {
    local c
    c="$(LC_ALL=C apt-cache policy "$1" 2>/dev/null | awk '/^[[:space:]]*Candidate:/{print $2; exit}')"
    [[ -n "$c" && "$c" != "(none)" ]]
}

# apt 系: 从给定包名列表中返回第一个存在候选的包名(均无候选则输出为空)
apt_first_candidate() {
    local p
    for p in "$@"; do
        apt_has_candidate "$p" && { echo "$p"; return 0; }
    done
    return 1
}

# Ubuntu 精简容器/部分镜像常只启用 main 组件, 而 mysql-server/mariadb-server/maven 等位于 universe;
# 探测到候选缺失时, 尝试在指向 Ubuntu 仓库的源上补开 universe 并刷新索引(幂等, 失败容错)。
# 仅在 ID=ubuntu 时生效; Debian 的 mariadb-server/maven 均在 main, 无需处理
apt_enable_universe() {
    [[ "$ID" == "ubuntu" ]] || return 1
    local f tmp modified=0
    # 判断某源文件是否指向 Ubuntu 仓库(文件名含 ubuntu, 或 URIs 指向官方源/镜像站)
    src_is_ubuntu() {
        case "$1" in
            *ubuntu*) grep -Eq '^(URIs:|deb )' "$1" ;;
            *)        grep -Eq '^(URIs:|deb ).*(archive\.ubuntu\.com|ports\.ubuntu\.com|security\.ubuntu\.com|mirrors\.)' "$1" ;;
        esac
    }
    # 1) deb822 格式: /etc/apt/sources.list.d/*.sources (Ubuntu 22.04+ 默认)
    for f in /etc/apt/sources.list.d/*.sources; do
        [[ -f "$f" ]] || continue
        src_is_ubuntu "$f" || continue
        tmp="${f}.jshERP.tmp"
        awk '/^[[:space:]]*Components:/ {
                 if ($0 !~ /(^|[[:space:]])universe([[:space:]]|$)/) { sub(/[[:space:]]+$/, ""); print $0 " universe"; next }
             }
             { print }' "$f" > "$tmp" && mv "$tmp" "$f" && modified=1
    done
    # 2) 传统单行格式: /etc/apt/sources.list 与 sources.list.d/*.list (官方源/常见镜像站/云厂商内网源)
    for f in /etc/apt/sources.list /etc/apt/sources.list.d/*.list; do
        [[ -f "$f" ]] || continue
        src_is_ubuntu "$f" || continue
        tmp="${f}.jshERP.tmp"
        awk '/^[[:space:]]*deb([[:space:]]|$)/ {
                 if ($0 !~ /(^|[[:space:]])universe([[:space:]]|$)/) { sub(/[[:space:]]+$/, ""); print $0 " universe"; next }
             }
             { print }' "$f" > "$tmp" && mv "$tmp" "$f" && modified=1
    done
    [[ "$modified" == "1" ]] || return 1
    apt_refresh || true
    ok "已尝试启用 universe 组件并刷新软件源"
    return 0
}

# Ubuntu 已 EOL(非 LTS / 超出维护期)的版本, archive/security.ubuntu.com 已停止提供,
# apt-get update 会对仓库报 404 / "does not have a Release file"(如 oracular=24.10, 2025-07 起下线)。
# 将官方源重定向到 old-releases.ubuntu.com(长期保留全部 EOL 版本, 含 universe, 亦含 -updates/-security)。
# 仅处理指向官方源的条目(不影响自建镜像/第三方源); 无改动时返回 1。
apt_fix_eol_ubuntu() {
    [[ "$ID" == "ubuntu" ]] || return 1
    local f tmp modified=0
    for f in /etc/apt/sources.list /etc/apt/sources.list.d/*.list /etc/apt/sources.list.d/*.sources; do
        [[ -f "$f" ]] || continue
        grep -Eq 'archive\.ubuntu\.com|security\.ubuntu\.com|ports\.ubuntu\.com' "$f" || continue
        tmp="${f}.jshERP-eol.tmp"
        sed -E 's@(https?://)(archive|security|ports)\.ubuntu\.com@\1old-releases.ubuntu.com@g' "$f" > "$tmp"
        if cmp -s "$f" "$tmp"; then rm -f "$tmp"; continue; fi
        mv "$tmp" "$f"; modified=1
        warn "检测到 Ubuntu 已 EOL(官方源 404), 已切换软件源: $f -> old-releases.ubuntu.com"
    done
    [[ "$modified" == "1" ]]
}

# 统一的 apt 索引刷新入口:
#   - 成功静默返回 0;
#   - 失败且判定为 Ubuntu EOL(官方源 404)时, 自动切换 old-releases 源并重试一次;
#   - 仍失败则打印错误摘要并返回非 0, 由调用方决定告警/容错
apt_refresh() {
    local out rc
    out="$(apt-get update 2>&1)"
    rc=$?
    [[ "$rc" == "0" ]] && return 0
    if [[ "$ID" == "ubuntu" ]] && grep -Eq '404|does not have a Release file' <<<"$out"; then
        if apt_fix_eol_ubuntu; then
            if apt-get update >/dev/null 2>&1; then return 0; fi
            warn "切换 old-releases.ubuntu.com 后 apt-get update 仍失败, 完整输出:"
            apt-get update 2>&1 | tail -n 12 || true
            return 1
        fi
    fi
    warn "apt-get update 未成功(网络/软件源异常?), 错误摘要:"
    grep -E '^(Err|E):' <<<"$out" | head -n 8 | sed 's/^/  /'
    return 1
}

# apt 系: 给定包列表存在无候选的情况时(索引过期 / Ubuntu 仅启用 main / 发行版 EOL 等),
# 经 apt_refresh(含 EOL 自动切换)恢复; 仍缺再尝试补开 universe(Ubuntu 专属)。
# 完成后由调用方重新探测候选。
apt_ensure_candidates() {
    local p any_missing=0
    for p in "$@"; do
        apt_has_candidate "$p" || any_missing=1
    done
    [[ "$any_missing" == "0" ]] && return 0
    apt_refresh || true
    local still=0
    for p in "$@"; do
        apt_has_candidate "$p" || still=1
    done
    [[ "$still" == "0" ]] && return 0
    apt_enable_universe || return 1
    return 0
}

usage() {
    sed -n '2,40p' "$0" | sed 's/^# \{0,1\}//'
    exit 0
}

# 解析参数
for arg in "$@"; do
    case "$arg" in
        -h|--help) usage ;;
        --skip-deps) DO_DEPS=0 ;;
        --skip-build) DO_BUILD=0 ;;
        *) die "未知参数: $arg（可用 --help 查看帮助）" ;;
    esac
done

# 系统信息与包管理器
ID="$( (. /etc/os-release; echo "${ID:-unknown}") 2>/dev/null || echo unknown )"
if cmd_exists dnf; then      PM=dnf
elif cmd_exists yum; then    PM=yum
elif cmd_exists apt-get; then PM=apt-get
else die "仅支持 Debian/Ubuntu(CentOS/Rocky/Alma) 系 Linux"; fi
if [[ "$PM" == "apt-get" ]]; then PM_INSTALL=(apt-get install -y)
else PM_INSTALL=("$PM" install -y); fi
# 供报错/排障提示使用, 避免在 Debian/Ubuntu 上给出 yum 这类错误命令
case "$PM" in
    apt-get) PM_HINT="apt-get install -y" ;;
    dnf)     PM_HINT="dnf install -y" ;;
    *)       PM_HINT="yum install -y" ;;
esac

pkg_install() {
    local missing=() p out rc
    for p in "$@"; do
        cmd_exists "$p" || missing+=("$p")
    done
    [[ ${#missing[@]} -eq 0 ]] && return 0
    log "安装缺失依赖: ${missing[*]}"
    if [[ "$PM" == "apt-get" ]]; then
        # 避免 debconf 交互提问挂起(如 tzdata/locales)
        export DEBIAN_FRONTEND=noninteractive
        apt_refresh || true
    fi
    # LC_ALL=C: 中文 locale 下 apt 报错(如"无法定位软件包")不利于脚本判断, 统一为英文后便于识别
    out="$(LC_ALL=C "${PM_INSTALL[@]}" "${missing[@]}" 2>&1)"
    rc=$?
    if [[ "$rc" != "0" ]]; then
        # apt 的 "Unable to locate package" 多为源索引过期或组件不全(如 Ubuntu 只开 main):
        # 刷新索引后重试一次, 仍失败时按 PKG_ALLOW_FAIL 决定容错或报错
        if [[ "$PM" == "apt-get" ]] && grep -q 'Unable to locate package' <<<"$out"; then
            warn "软件源未找到部分软件包(索引过期/源组件不全/发行版 EOL?), 刷新索引后重试一次 ..."
            apt_refresh || true
            if LC_ALL=C "${PM_INSTALL[@]}" "${missing[@]}"; then return 0; fi
        fi
        printf '%s\n' "$out" >&2   # 展示原始报错, 便于定位是网络/源/依赖问题
        if [[ "${PKG_ALLOW_FAIL:-0}" == "1" ]]; then
            warn "安装 ${missing[*]} 失败(已容错, 由调用方决定回退)"
            return 1
        fi
        die "安装 ${missing[*]} 失败，请检查网络/软件源后重试"
    fi
}

ensure_epel() {
    [[ "$PM" == "apt-get" ]] && return 0
    # 已启用则跳过; 失败容错(部分发行版 AppStream 已含所需包, 无需 EPEL)
    rpm -q epel-release >/dev/null 2>&1 && return 0
    "$PM" install -y epel-release >/dev/null 2>&1 || true
}

svc_restart() {
    local s
    for s in "$@"; do
        if systemctl >/dev/null 2>&1; then
            # 刚通过包管理器安装的单元可能尚未被 systemd 加载, 先刷新缓存
            systemctl daemon-reload >/dev/null 2>&1 || true
            if systemctl list-unit-files 2>/dev/null | grep "^${s}\.service" >/dev/null; then
                systemctl enable "${s}" >/dev/null 2>&1 || true
                # 单元处于 failed 状态会拦截 start/restart, 清态后再启
                systemctl reset-failed "${s}" >/dev/null 2>&1 || true
                if systemctl restart "${s}"; then ok "服务 ${s} 已重启"; return 0; fi
                [[ "${JSH_SILENT:-0}" == "1" ]] || warn "服务 ${s} 重启失败, 可执行 systemctl status ${s} 查看原因"
            fi
        else
            service "${s}" restart >/dev/null 2>&1 && { ok "服务 ${s} 已重启"; return 0; }
        fi
    done
    [[ "${JSH_SILENT:-0}" == "1" ]] || warn "无法自动管理服务($*) , 请手动启动"
    return 1
}

server_ip() { hostname -I 2>/dev/null | awk '{print $1}'; }

# ---------------------------- 1. 环境安装 ----------------------------
ensure_java8() {
    log "[环境] 检测 JDK ..."
    local jdk_dir=/opt/java/temurin8
    # 判断某个 java 可执行文件是否为 JDK8
    local java_bin=""
    local found=0

    if java -version 2>&1 | grep '"1\.8' >/dev/null; then
        java_bin="$(command -v java)"
        found=1
    fi
    # 已下载过的 Temurin8 直接复用, 避免重复下载
    if [[ "$found" != "1" && -x "$jdk_dir/bin/java" ]]; then
        if "$jdk_dir/bin/java" -version 2>&1 | grep '"1\.8' >/dev/null; then
            java_bin="$jdk_dir/bin/java"
            found=1
            log "复用已安装的 Temurin8: $jdk_dir"
        fi
    fi

    if [[ "$found" != "1" ]]; then
        warn "未找到 JDK8，尝试安装 ..."
        if [[ "$PM" == "apt-get" ]]; then
            if apt-cache show openjdk-8-jdk >/dev/null 2>&1; then
                PKG_ALLOW_FAIL=1 pkg_install openjdk-8-jdk || true
            fi
        else
            # CentOS/Rocky/Alma 若无 openjdk8 包则容错, 由下方 Temurin 下载兜底
            PKG_ALLOW_FAIL=1 pkg_install java-1.8.0-openjdk-devel || true
        fi
        if java -version 2>&1 | grep '"1\.8' >/dev/null; then
            java_bin="$(command -v java)"
            found=1
        fi
    fi

    if [[ "$found" != "1" ]]; then
        log "发行版无 JDK8 包，下载 Temurin8(Adoptium) 到 $jdk_dir ..."
        local arch
        case "$(uname -m)" in x86_64) arch=x64;; aarch64) arch=aarch64;; *) die "不支持的 CPU 架构: $(uname -m)";; esac
        mkdir -p "$jdk_dir"
        curl -fL --connect-timeout 20 -o /tmp/temurin8.tar.gz \
            "https://api.adoptium.net/v3/binary/latest/8/ga/linux/${arch}/jdk/hotspot/normal/eclipse" \
            || die "下载 Temurin8 失败，请手动安装 JDK8 后重试(--skip-deps 可跳过)"
        tar -xzf /tmp/temurin8.tar.gz -C "$jdk_dir" --strip-components=1 || die "解压 Temurin8 失败"
        java_bin="$jdk_dir/bin/java"
    fi

    # 让本脚本进程及后续子进程(mvn/run-manage 等)优先使用 JDK8
    JAVA_HOME="$(dirname "$(dirname "$java_bin")")"
    export JAVA_HOME
    export PATH="$JAVA_HOME/bin:$PATH"
    # 写入 profile.d(登录 shell 生效); 脚本进程内已通过上面 export 生效
    echo "export JAVA_HOME=$JAVA_HOME" > /etc/profile.d/jshERP-env.sh
    echo "export PATH=\$JAVA_HOME/bin:\$PATH" >> /etc/profile.d/jshERP-env.sh
    ok "Java 就绪: $("$JAVA_HOME/bin/java" -version 2>&1 | head -1)"
}

ensure_maven() {
    log "[环境] 检测 Maven ..."
    if ! cmd_exists mvn; then
        # apt 源无 maven 候选(索引过期 / Ubuntu 仅 main)时先尝试恢复, 仍无候选再走二进制兜底
        if [[ "$PM" == "apt-get" ]]; then
            apt_ensure_candidates maven
            apt_has_candidate maven && PKG_ALLOW_FAIL=1 pkg_install maven || true
        else
            ensure_epel
            PKG_ALLOW_FAIL=1 pkg_install maven || true
        fi
        if ! cmd_exists mvn; then
            log "下载 Maven 3.9.9 到 /opt/maven ..."
            mkdir -p /opt/maven
            # dlcdn.apache.org 只保留最新版本, 固定版本(3.9.9)归档后返回 404;
            # archive.apache.org 长期保留全部版本, 作为备用源
            local mv_url dl_ok=0
            for mv_url in \
                "https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz" \
                "https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz"; do
                if curl -fL --connect-timeout 20 -o /tmp/maven.tar.gz "$mv_url"; then dl_ok=1; break; fi
                warn "下载失败: $mv_url(该源可能已归档/网络异常), 尝试下一个备用源 ..."
            done
            [[ "$dl_ok" == "1" ]] || die "下载 Maven 失败，请手动安装后重试(--skip-deps 可跳过)"
            tar -xzf /tmp/maven.tar.gz -C /opt/maven --strip-components=1 || die "解压 Maven 失败"
            echo "export PATH=/opt/maven/bin:\$PATH" >> /etc/profile.d/jshERP-env.sh
            export PATH="/opt/maven/bin:$PATH"
        fi
    fi
    ok "Maven 就绪: $(mvn -v 2>/dev/null | head -1)"
}

ensure_node() {
    log "[环境] 检测 Node.js ..."
    local major=""
    if cmd_exists node; then major="$(node -v | sed 's/^v//; s/\..*//')"; fi
    if [[ -z "$major" || "$major" -lt 16 ]]; then
        if [[ "$PM" == "apt-get" ]]; then
            # 精简源(Ubuntu 仅 main)可能缺 nodejs/npm 候选, 先恢复源, 分开安装并容错;
            # 失败或仍无候选时由下方 Node 二进制包下载兜底
            apt_ensure_candidates nodejs npm
            apt_has_candidate nodejs && PKG_ALLOW_FAIL=1 pkg_install nodejs >/dev/null 2>&1 || true
            apt_has_candidate npm && PKG_ALLOW_FAIL=1 pkg_install npm >/dev/null 2>&1 || true
        else
            # CentOS/RHEL 默认仓库无 nodejs/npm(需 EPEL); 安装失败时由下方源码包下载兜底
            ensure_epel
            PKG_ALLOW_FAIL=1 pkg_install nodejs npm || true
        fi
        if cmd_exists node; then major="$(node -v | sed 's/^v//; s/\..*//')"; fi
    fi
    if [[ -z "$major" || "$major" -lt 16 ]]; then
        log "发行版 Node 版本过低，下载 Node 20 LTS 到 /opt/node ..."
        local arch
        case "$(uname -m)" in x86_64) arch=x64;; aarch64) arch=arm64;; *) die "不支持的 CPU 架构: $(uname -m)";; esac
        mkdir -p /opt/node
        curl -fL --connect-timeout 20 -o /tmp/node.tar.xz \
            "https://nodejs.org/dist/v20.19.0/node-v20.19.0-linux-${arch}.tar.xz" \
            || die "下载 Node 失败，请手动安装 Node>=16 后重试(--skip-deps 可跳过)"
        tar -xJf /tmp/node.tar.xz -C /opt/node --strip-components=1 || die "解压 Node 失败"
        echo "export PATH=/opt/node/bin:\$PATH" >> /etc/profile.d/jshERP-env.sh
        export PATH="/opt/node/bin:$PATH"
        major="$(node -v | sed 's/^v//; s/\..*//')"
    fi
    # Node17+ 编译 webpack4(vue-cli3) 需要兼容 openssl
    if [[ "$major" -ge 17 ]] && [[ "${NODE_OPTIONS:-}" != *openssl-legacy-provider* ]]; then
        export NODE_OPTIONS="--openssl-legacy-provider ${NODE_OPTIONS:-}"
        log "Node 主版本 ${major}，已追加 NODE_OPTIONS=--openssl-legacy-provider"
    fi
    # 按本机物理内存动态设置 Node 堆上限(上限取 min(4096, RAM*3/4), 保底 1024MB):
    # 小内存机若仍按 4096 封顶, V8 堆会尽量增长直至内存耗尽, 构建中途易被内核 OOM
    # killer 杀死, 日志无报错只见 "Killed"; 封顶略低于可用内存可显著缓解。
    local mem_mb heap_mb
    mem_mb="$(awk '/^MemTotal:/{print int($2/1024)}' /proc/meminfo 2>/dev/null)"
    [[ "$mem_mb" =~ ^[0-9]+$ ]] || mem_mb=2048
    heap_mb=$(( mem_mb * 3 / 4 ))
    (( heap_mb > 4096 )) && heap_mb=4096
    (( heap_mb < 1024 )) && heap_mb=1024
    export NODE_OPTIONS="${NODE_OPTIONS:-} --max-old-space-size=${heap_mb}"
    ok "Node 就绪: $(node -v) / npm $(npm -v 2>/dev/null) (堆上限 ${heap_mb}MB)"
}

# 低内存机器(常见 1-2G VPS)上, 前端 vue-cli/webpack 生产构建实际占用常超 2GB。
# 若无 swap, node 进程会因内存耗尽被内核 OOM killer 杀死(日志无报错, 仅 "Killed")。
# 这里检测 内存+Swap 总量, 不足 JSH_SWAP_TARGET_MB(默认 4096)时自动补建 swapfile 并写入 fstab。
ensure_swap() {
    local mem_mb=0 swap_mb=0 total_mb=0 need_mb=0 avail_kb=0
    local sw_file=/swapfile_jshERP
    if [[ -f /proc/meminfo ]]; then
        mem_mb="$(awk '/^MemTotal:/{print int($2/1024)}' /proc/meminfo 2>/dev/null)"
        swap_mb="$(awk '/^SwapTotal:/{print int($2/1024)}' /proc/meminfo 2>/dev/null)"
    fi
    [[ "$mem_mb" =~ ^[0-9]+$ ]] || mem_mb=0
    [[ "$swap_mb" =~ ^[0-9]+$ ]] || swap_mb=0
    local target_mb="${JSH_SWAP_TARGET_MB:-4096}"
    total_mb=$(( mem_mb + swap_mb ))
    if (( total_mb >= target_mb )); then
        log "[内存] 内存+Swap 充足 (RAM ${mem_mb}MB + Swap ${swap_mb}MB), 可正常构建"
        return 0
    fi
    # 已启用同名 swapfile 则无需重复创建
    if swapon -s 2>/dev/null | grep -q "${sw_file}$"; then
        ok "[内存] 已启用 ${sw_file}, 构建内存可用"
        return 0
    fi
    need_mb=$(( target_mb - total_mb ))
    avail_kb="$(df -Pk / 2>/dev/null | awk 'NR==2{print $4}')"
    [[ "$avail_kb" =~ ^[0-9]+$ ]] || avail_kb=0
    if (( avail_kb < need_mb * 1100 )); then
        warn "[内存] RAM+Swap 仅 ${total_mb}MB 且根分区磁盘空间不足($((avail_kb/1024))MB), 无法自动补建 swap, 构建可能失败"
        warn "[内存] 请手动执行: dd if=/dev/zero of=${sw_file} bs=1M count=${need_mb} && chmod 600 ${sw_file} && mkswap ${sw_file} && swapon ${sw_file}"
        return 1
    fi
    log "[内存] RAM+Swap 共 ${total_mb}MB 偏低, 自动补建 ${need_mb}MB swapfile: ${sw_file} ..."
    if ! fallocate -l "${need_mb}M" "$sw_file" 2>/dev/null; then
        dd if=/dev/zero of="$sw_file" bs=1M count="$need_mb" 2>/dev/null || { warn "[内存] 创建 swapfile 失败"; return 1; }
    fi
    chmod 600 "$sw_file"
    if ! mkswap "$sw_file" >/dev/null 2>&1; then
        warn "[内存] mkswap ${sw_file} 失败"; rm -f "$sw_file"; return 1
    fi
    if ! swapon "$sw_file" >/dev/null 2>&1; then
        # 部分文件系统上 fallocate 预分配的文件无法直接 swapon, 用 dd 真实写入兜底重试
        warn "[内存] swapon 失败, 改用 dd 重建 swapfile 重试 ..."
        rm -f "$sw_file"
        dd if=/dev/zero of="$sw_file" bs=1M count="$need_mb" 2>/dev/null || { warn "[内存] 重建 swapfile 失败"; return 1; }
        chmod 600 "$sw_file"
        mkswap "$sw_file" >/dev/null 2>&1 || { warn "[内存] mkswap 失败"; rm -f "$sw_file"; return 1; }
        swapon "$sw_file" >/dev/null 2>&1 || { warn "[内存] swapon 失败(容器内无 CAP_SYS_ADMIN?), 请手动添加 swap 或改用内存更大的机器构建"; rm -f "$sw_file"; return 1; }
    fi
    grep -q "^${sw_file}" /etc/fstab 2>/dev/null || echo "${sw_file} none swap sw 0 0" >> /etc/fstab
    ok "已补建并启用 ${need_mb}MB swap (${sw_file}), 构建可用内存约 $(( total_mb + need_mb ))MB"
}

# 在 PATH 与常见安装目录中定位可执行文件(避免因 PATH 不含 /usr/sbin 等目录而误判未安装)
find_bin_path() {
    local c d p
    for c in "$@"; do
        p="$(command -v "$c" 2>/dev/null)" && { echo "$p"; return 0; }
    done
    for c in "$@"; do
        for d in /usr/local/sbin /usr/sbin /usr/libexec /usr/local/bin /usr/bin; do
            [[ -x "$d/$c" ]] && { echo "$d/$c"; return 0; }
        done
    done
    return 1
}

# 读取数据库数据目录(解析 my.cnf, 缺省 /var/lib/mysql)
db_datadir() {
    local cnf d
    for cnf in /etc/my.cnf /etc/mysql/my.cnf /etc/mysql/mariadb.conf.d/*.cnf \
                /etc/my.cnf.d/*.cnf /etc/mariadb/my.cnf; do
        [[ -f "$cnf" ]] || continue
        d="$(awk '/^[[:space:]]*datadir[[:space:]]*=/ {
                line=$0; sub(/^[^=]*=[[:space:]]*/, "", line)
                sub(/[[:space:]]*[#;].*$/, "", line); gsub(/[[:space:]]+$/, "", line)
                print line; exit }' "$cnf" 2>/dev/null)"
        [[ -n "$d" ]] && { echo "$d"; return 0; }
    done
    echo /var/lib/mysql
}

# 数据目录初始化修复: 解决 CentOS7/RHEL7(MariaDB 5.5) 装包后常见的数据目录半初始化问题。
# 症状: mysqld 启动报 "Table 'mysql.host' doesn't exist" / "Can't open the mysql.plugin table";
# 原因: 装包时 mysql_install_db 未执行成功, 或 /var/lib/mysql 残留残缺文件(InnoDB 已建、系统表未建)。
# 仅当数据目录缺少系统库关键表时才触发; 已初始化的正常库直接跳过, 不会误删用户数据(残缺目录先备份)。
repair_db_datadir() {
    local ddir tool back sys_ok=0
    ddir="$(db_datadir)"
    # 系统库关键表存在即视为已初始化: MariaDB<=10.3/MySQL<=5.6 为 MyISAM(user.frm),
    # MySQL 5.7+/MariaDB 10.4+ 为 InnoDB(user.ibd)
    if [[ -d "$ddir/mysql" ]]; then
        if ls "$ddir"/mysql/user.frm "$ddir"/mysql/host.frm >/dev/null 2>&1 \
           || ls "$ddir"/mysql/user.ibd "$ddir"/mysql/host.ibd >/dev/null 2>&1; then
            sys_ok=1
        fi
    fi
    [[ "$sys_ok" == "1" ]] && return 0
    warn "数据目录 $ddir 缺少系统表, 疑似未初始化/初始化中断, 尝试自动修复 ..."
    tool="$(find_bin_path mariadb-install-db mysql_install_db)"
    if [[ -z "$tool" ]]; then
        warn "未找到初始化工具(mysql_install_db/mariadb-install-db), 可手动执行: ${PM_HINT} mariadb-server(或 mysql-server)"
        return 1
    fi
    # 停止可能占用数据目录的进程
    pkill -9 mysqld >/dev/null 2>&1 || true
    pkill -9 mariadbd >/dev/null 2>&1 || true
    sleep 1
    # 目录非空(残缺/旧数据)先整体备份, 避免误删
    if [[ -d "$ddir" ]] && [[ -n "$(ls -A "$ddir" 2>/dev/null)" ]]; then
        back="${ddir}.backup.$(date '+%Y%m%d%H%M%S')"
        mv "$ddir" "$back" 2>/dev/null && warn "原数据目录(未完整初始化)已备份为: $back"
    fi
    mkdir -p "$ddir"
    chown mysql:mysql "$ddir" 2>/dev/null || true
    if ! "$tool" --user=mysql --datadir="$ddir" >/dev/null 2>&1; then
        # 个别版本不支持 --datadir 参数时回退到默认目录(与 my.cnf 一致)
        if ! "$tool" --user=mysql >/dev/null 2>&1; then
            warn "系统表初始化失败, 可手动执行: ${tool} --user=mysql --datadir=${ddir}"
            return 1
        fi
    fi
    chown -R mysql:mysql "$ddir" 2>/dev/null || true
    # SELinux Enforcing 时恢复数据目录上下文, 否则 mysqld 无法读写
    if cmd_exists restorecon; then restorecon -Rv "$ddir" >/dev/null 2>&1 || true; fi
    ok "数据目录已重新初始化: $ddir"
    return 0
}

ensure_db() {
    log "[环境] 检测 MySQL/MariaDB ..."
    # 注意: mysql 客户端与服务端在多数发行版是相互独立的包;
    # 且 mysqld/mariadbd 通常装在 /usr/sbin 等目录(非登录 shell/su 切换的 root 其 PATH 可能不含该目录),
    # 故统一用 find_bin_path 做 PATH + 常见目录双重定位, 避免误判"未安装"
    local DBD_BIN="" have_srv=0
    DBD_BIN="$(find_bin_path mysqld mariadbd)"
    [[ -n "$DBD_BIN" ]] && have_srv=1

    if [[ "$have_srv" == "1" ]]; then
        ok "检测到数据库服务端: $DBD_BIN"
    else
        log "未检测到数据库服务端, 开始安装 ..."
        local dbpkg=""
        if [[ "$PM" == "apt-get" ]]; then
            # 不按发行版写死, 而按"软件源实际提供的候选"择优安装:
            #   Debian 中 mysql-server 是虚拟包(实际由 mariadb-server 提供);
            #   Ubuntu 中 mysql-server/mariadb-server 均可为真实包;
            # 候选缺失(索引过期 / Ubuntu 仅 main)时先尝试恢复源(刷新索引 + universe)
            apt_ensure_candidates mysql-server mariadb-server
            dbpkg="$(apt_first_candidate mysql-server mariadb-server)"
            if [[ -n "$dbpkg" ]]; then
                log "通过系统包安装数据库服务端: ${dbpkg}"
                PKG_ALLOW_FAIL=1 pkg_install "$dbpkg" || true
            else
                warn "软件源中仍无数据库服务端候选(内网/精简源常见), 将给出排障提示"
            fi
        else
            # CentOS 7: 无 mysql-server 候选(自动回退 base 源 mariadb-server);
            # CentOS 8+/Rocky/Alma: AppStream 提供 MySQL 8 或 MariaDB
            PKG_ALLOW_FAIL=1 pkg_install mysql-server \
                || PKG_ALLOW_FAIL=1 pkg_install mariadb-server || true
        fi
        DBD_BIN="$(find_bin_path mysqld mariadbd)"
        [[ -n "$DBD_BIN" ]] && have_srv=1
        if [[ "$have_srv" != "1" ]]; then
            # 输出实际安装情况, 便于定位(而非只抛一句失败)
            warn "当前已安装的数据库相关软件包:"
            if cmd_exists rpm; then rpm -qa 2>/dev/null | grep -iE 'mysql|maria' | sed 's/^/    /'
            elif cmd_exists dpkg; then dpkg -l 2>/dev/null | grep -iE 'mysql|maria' | awk '{print "    " $2}'; fi
            warn "守护进程文件(mysqld/mariadbd)实际所在位置:"
            if cmd_exists rpm; then rpm -ql mysql-server mariadb-server 2>/dev/null | grep -E '/(mysqld|mariadbd)$' | sed 's/^/    /'; fi
            warn "若软件源受限(如 Ubuntu 仅启用 main / 内网镜像未同步 universe / 索引过期), 请先:"
            warn "  1) 检查软件源配置并执行: apt-get update(或 yum makecache)"
            warn "  2) 安装数据库: ${PM_HINT} mariadb-server(或 mysql-server)"
            warn "  修复后使用 --skip-deps 重新执行本脚本"
            die "安装后仍未定位到 mysqld/mariadbd, 请按上方信息检查软件源与网络"
        fi
        ok "数据库服务端已安装: $DBD_BIN"
    fi
    # 服务端二进制存在不代表系统表已初始化: 缺失时先重建数据目录,
    # 避免出现 "Table 'mysql.host' doesn't exist" 这类启动失败(见 repair_db_datadir 注释)
    repair_db_datadir || warn "数据目录系统表异常, 将尝试直接启动(若仍失败, 请按下方排障提示手动执行 mysql_install_db)"
    # 客户端: 后续建库/导 SQL 均依赖 mysql 命令(部分发行版服务端包不随带客户端)
    if [[ -z "$(find_bin_path mysql)" ]]; then
        if [[ "$PM" == "apt-get" ]]; then
            PKG_ALLOW_FAIL=1 pkg_install mariadb-client mysql-client >/dev/null 2>&1 || true
        else
            PKG_ALLOW_FAIL=1 pkg_install mariadb mysql >/dev/null 2>&1 || true
        fi
    fi
    if [[ -z "$(find_bin_path mysql)" ]]; then
        die "缺少 mysql 客户端命令, 请先安装客户端(mariadb/mysql / mariadb-client/mysql-client)后重试"
    fi

    # ---- 启动数据库服务(新装系统服务名各不相同, 分组尝试) ----
    local srv_ok=0
    if systemctl >/dev/null 2>&1; then
        JSH_SILENT=1 svc_restart mysql mariadb && srv_ok=1
        [[ "$srv_ok" == "1" ]] || { JSH_SILENT=1 svc_restart mysqld mariadbd && srv_ok=1; }
        # 单元处于 failed 状态会拦截再次启动(systemd 保留上次失败记录), 清态后重试一轮
        if [[ "$srv_ok" != "1" ]]; then
            systemctl reset-failed mysql mariadb mysqld mariadbd >/dev/null 2>&1 || true
            JSH_SILENT=1 svc_restart mysql mariadb && srv_ok=1
            [[ "$srv_ok" == "1" ]] || { JSH_SILENT=1 svc_restart mysqld mariadbd && srv_ok=1; }
        fi
    else
        # 无 systemd 环境(部分容器/老系统): 直接走 service 脚本
        service mariadb start >/dev/null 2>&1 && srv_ok=1
        [[ "$srv_ok" == "1" ]] || { service mysql start  >/dev/null 2>&1 && srv_ok=1; }
        [[ "$srv_ok" == "1" ]] || { service mysqld start >/dev/null 2>&1 && srv_ok=1; }
    fi
    if [[ "$srv_ok" != "1" ]]; then
        # 兜底: 直接拉起守护进程(等价于手动启动); 若仍失败, 下方日志/状态会给出原因
        warn "systemctl/service 启动失败, 尝试直接启动数据库守护进程 ..."
        mkdir -p /var/run/mysqld /var/run/mariadb 2>/dev/null || true
        chown mysql:mysql /var/run/mysqld /var/run/mariadb 2>/dev/null || true
        local safe_bin
        safe_bin="$(find_bin_path mysqld_safe mysqld mariadbd)"
        if [[ -n "$safe_bin" ]]; then
            nohup "$safe_bin" --user=mysql >/dev/null 2>&1 &
        fi
    fi

    # ---- 就绪自检(最长约 60 秒) ----
    local i up=0 sock
    for i in $(seq 1 30); do
        if mysql -uroot -e "SELECT 1" >/dev/null 2>&1; then up=1; break; fi
        # root 采用密码/socket 认证时上述登录失败不代表服务未起, 以进程+套接字兜底判断
        if pgrep -x mysqld >/dev/null 2>&1 || pgrep -x mariadbd >/dev/null 2>&1; then
            for sock in /var/lib/mysql/mysql.sock /var/run/mysqld/mysqld.sock \
                        /var/run/mariadb/mariadb.sock /tmp/mysql.sock; do
                [[ -S "$sock" ]] && { up=1; break; }
            done
            [[ "$up" == "1" ]] && break
        fi
        sleep 2
    done
    if [[ "$up" != "1" ]]; then
        warn "数据库服务 60 秒内未就绪, 以下是排障信息:"
        warn "  查看状态: systemctl status mariadb mysql mysqld --no-pager -l"
        warn "  查看日志: tail -n 50 /var/log/mariadb/mariadb.log /var/log/mysql/error.log"
        warn "  手动启动: systemctl start mariadb; 无 systemd 时执行: mysqld_safe --user=mysql &"
        echo
        # 识别"系统表缺失"类报错(数据目录未完整初始化), 给出针对性修复命令
        local ddir dberr
        ddir="$(db_datadir)"
        dberr="$( { tail -n 60 /var/log/mariadb/mariadb.log 2>/dev/null
                    tail -n 60 /var/log/mysql/error.log 2>/dev/null
                    tail -n 60 /var/log/mysqld.log 2>/dev/null; } 2>/dev/null )"
        if grep -qE "mysql\.host doesn't exist|Can't open and lock privilege tables|mysql\.plugin table" <<<"$dberr"; then
            warn "日志显示数据库系统表缺失(数据目录未完整初始化), 请执行:"
            warn "  mv ${ddir} ${ddir}.broken && mkdir -p ${ddir} && chown mysql:mysql ${ddir}"
            warn "  mysql_install_db --user=mysql && chown -R mysql:mysql ${ddir} && systemctl start mariadb"
            warn "修复后重新执行: bash $0 --skip-deps"
        fi
        echo
        systemctl --no-pager status mariadb mysql mysqld 2>/dev/null | head -30 || true
        echo
        tail -n 30 /var/log/mariadb/mariadb.log 2>/dev/null || true
        tail -n 30 /var/log/mysql/error.log 2>/dev/null || true
        die "数据库未能启动, 请按上方信息修复后, 使用 --skip-deps 重新执行本脚本"
    fi
    ok "MySQL/MariaDB 服务可用: $(mysql --version 2>/dev/null | sed 's/, for .*//')"
}

ensure_redis() {
    log "[环境] 检测 Redis ..."
    if ! cmd_exists redis-server && ! cmd_exists redis-cli; then
        if [[ "$PM" == "apt-get" ]]; then
            # apt-cache show 对虚拟包/缺失包均返回 0, 须按候选判断实际可用包名;
            # redis-server/redis 在 Ubuntu 中位于 universe, 先做候选恢复
            apt_ensure_candidates redis-server redis
            if apt_has_candidate redis-server; then pkg_install redis-server
            elif apt_has_candidate redis; then pkg_install redis
            else die "软件源中无 redis-server/redis 候选(索引过期或未启用 universe?), 请检查软件源后重试"; fi
        else
            ensure_epel
            pkg_install redis
        fi
    fi

    # 服务管理重启; 失败时(容器内无 systemd/service)直接拉起守护进程
    local rc=""
    for f in /etc/redis/redis.conf /etc/redis.conf; do [[ -f "$f" ]] && rc="$f" && break; done
    if [[ -n "$rc" ]]; then
        # 先写密码, 再启动, 避免服务在旧配置(无密码)下运行
        sed -i "s/^#\? *requirepass .*/requirepass ${REDIS_PASSWORD}/" "$rc"
        grep -q "^requirepass " "$rc" || echo "requirepass ${REDIS_PASSWORD}" >> "$rc"
        ok "Redis 密码已写入 $rc"
    else
        warn "未找到 Redis 配置文件, 请手动设置 requirepass ${REDIS_PASSWORD}"
    fi

    if svc_restart redis-server redis; then
        :
    else
        # 容器/无 systemd 环境兜底: 直接拉起 redis-server
        warn "无法通过 systemctl/service 管理 Redis, 尝试直接启动 redis-server ..."
        pkill -9 redis-server >/dev/null 2>&1 || true
        sleep 1
        if [[ -n "$rc" ]]; then
            nohup redis-server "$rc" >/dev/null 2>&1 &
        else
            nohup redis-server --requirepass "$REDIS_PASSWORD" >/dev/null 2>&1 &
        fi
    fi

    # 就绪自检(带密码认证)
    local i rdy=0
    for i in $(seq 1 15); do
        if redis-cli -a "$REDIS_PASSWORD" ping 2>/dev/null | grep -q PONG; then rdy=1; break; fi
        sleep 1
    done
    if [[ "$rdy" == "1" ]]; then
        ok "Redis 服务可用(密码认证)"
    else
        warn "Redis 未能就绪, 请手动启动: redis-server $rc"
    fi
}

ensure_nginx() {
    log "[环境] 检测 Nginx ..."
    if ! cmd_exists nginx; then
        if [[ "$PM" == "apt-get" ]]; then apt_ensure_candidates nginx
        else ensure_epel; fi
        pkg_install nginx
    fi
    # 部分发行版(Debian)默认不预建 conf.d, 站点配置写入前先确保目录存在
    mkdir -p /etc/nginx/conf.d /etc/nginx/sites-available /etc/nginx/sites-enabled 2>/dev/null || true
    svc_restart nginx
    ok "Nginx 可用: $(nginx -v 2>&1)"
}

# ---------------------------- 2. 数据库初始化 ----------------------------
init_database() {
    log "[数据库] 初始化 ${DB_NAME} ..."
    [[ -f "$SQL_FILE" ]] || die "未找到数据库脚本: $SQL_FILE"

    local MYSQL_ADMIN=(mysql -uroot)
    if [[ -n "$MYSQL_ROOT_PASSWORD" ]]; then MYSQL_ADMIN=(mysql -uroot -p"$MYSQL_ROOT_PASSWORD"); fi

    if ! "${MYSQL_ADMIN[@]}" -N -e "SELECT 1" >/dev/null 2>&1; then
        if [[ -z "$MYSQL_ROOT_PASSWORD" ]]; then
            die "无法以系统 root 免密登录 MySQL。若 root 有密码请设置环境变量 MYSQL_ROOT_PASSWORD 后重试"
        fi
        die "MySQL root 登录失败, 请检查 MYSQL_ROOT_PASSWORD"
    fi

    # MySQL 8.0 移除了 GRANT...IDENTIFIED BY 老语法;
    # 而 MariaDB 5.5(CentOS 7 默认)/旧版不支持 CREATE USER IF NOT EXISTS / ALTER USER,
    # 故按服务器版本分支处理建库建号
    local dbver is_mysql8
    dbver="$("${MYSQL_ADMIN[@]}" -N -e "SELECT VERSION()" 2>/dev/null | head -1)"
    is_mysql8=0
    case "$dbver" in 8.*|9.*) is_mysql8=1 ;; esac
    if [[ "$is_mysql8" == "1" ]]; then
        "${MYSQL_ADMIN[@]}" <<SQL
CREATE DATABASE IF NOT EXISTS \`${DB_NAME}\` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE USER IF NOT EXISTS '${DB_USER}'@'localhost'  IDENTIFIED BY '${DB_PASSWORD}';
CREATE USER IF NOT EXISTS '${DB_USER}'@'127.0.0.1' IDENTIFIED BY '${DB_PASSWORD}';
ALTER USER '${DB_USER}'@'localhost'  IDENTIFIED BY '${DB_PASSWORD}';
ALTER USER '${DB_USER}'@'127.0.0.1' IDENTIFIED BY '${DB_PASSWORD}';
GRANT ALL PRIVILEGES ON \`${DB_NAME}\`.* TO '${DB_USER}'@'localhost';
GRANT ALL PRIVILEGES ON \`${DB_NAME}\`.* TO '${DB_USER}'@'127.0.0.1';
FLUSH PRIVILEGES;
SQL
    else
        # MariaDB / MySQL 5.x: GRANT ... IDENTIFIED BY 兼容语法
        # (用户不存在则自动创建, 已存在则同步密码并授权)
        "${MYSQL_ADMIN[@]}" <<SQL
CREATE DATABASE IF NOT EXISTS \`${DB_NAME}\` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON \`${DB_NAME}\`.* TO '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASSWORD}';
GRANT ALL PRIVILEGES ON \`${DB_NAME}\`.* TO '${DB_USER}'@'127.0.0.1' IDENTIFIED BY '${DB_PASSWORD}';
FLUSH PRIVILEGES;
SQL
    fi
    ok "数据库 ${DB_NAME} 与账号 ${DB_USER} 已就绪 (${dbver})"

    local tables
    tables="$("${MYSQL_ADMIN[@]}" -N -e "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='${DB_NAME}'")"
    if [[ -z "$tables" || "$tables" == "0" ]]; then
        log "开始导入 ${SQL_FILE##*/} (空库首次导入)..."
        "${MYSQL_ADMIN[@]}" "${DB_NAME}" < "$SQL_FILE" || die "SQL 导入失败，请检查脚本与数据库兼容性"
        tables="$("${MYSQL_ADMIN[@]}" -N -e "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='${DB_NAME}'")"
    fi
    [[ -z "$tables" || "$tables" == "0" ]] && die "数据库导入后仍无表, 请人工检查"
    ok "数据库初始化完成, 当前 ${tables} 张表"

    if ! mysql -u"$DB_USER" -p"$DB_PASSWORD" -h"$DB_HOST" -P"$DB_PORT" -N -e "SELECT 1" >/dev/null 2>&1; then
        die "应用账号 ${DB_USER} 连接 ${DB_HOST}:${DB_PORT}/${DB_NAME} 失败, 请检查"
    fi
    ok "应用账号可正常连接数据库"
}

# ---------------------------- 3. 源码编译 ----------------------------
# 为 Maven 生成独立 settings(带镜像), 避免 Maven Central 429 限流/网络不稳定
setup_maven_mirror() {
    [[ -n "$MAVEN_MIRROR" ]] || return 0
    local m2="${HOME}/.m2"
    mkdir -p "$m2"
    local settings="$m2/jshERP-settings.xml"
    cat > "$settings" <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <mirrors>
    <mirror>
      <id>jshERP-mirror</id>
      <mirrorOf>central</mirrorOf>
      <name>jshERP mirror</name>
      <url>${MAVEN_MIRROR}</url>
    </mirror>
  </mirrors>
</settings>
EOF
    MAVEN_SETTINGS="$settings"
    ok "Maven 镜像已配置: ${MAVEN_MIRROR}"
}

build_backend() {
    log "[构建] 编译后端 jshERP-boot ..."
    cmd_exists mvn || die "缺少 Maven, 请先安装或使用 --skip-deps(前提: 已有环境) "
    cmd_exists java || die "缺少 Java"
    cd "$BACKEND_SRC" || die "后端目录不存在: $BACKEND_SRC"

    setup_maven_mirror
    local mvn_args=(-B package -DskipTests)
    [[ -n "${MAVEN_SETTINGS:-}" ]] && mvn_args=(-s "$MAVEN_SETTINGS" -B package -DskipTests)

    local pom="${BACKEND_SRC}/pom.xml" patched=0
    # 仓库 pom.xml 中 maven-compiler-plugin 默认 skip=true(官方源如此),
    # 会导致命令行打包不产出 .class, 这里在构建时临时放开, 构建后还原
    if grep -q '<skip>true</skip>' "$pom"; then
        log "检测到 pom.xml compiler skip=true, 构建时临时改为 false(构建后自动还原)"
        sed -i 's|<skip>true</skip>|<skip>false</skip>|' "$pom"
        patched=1
    fi

    log "执行 mvn package (首次会下载依赖, 耗时较长)..."
    if ! mvn "${mvn_args[@]}"; then
        # 镜像仓库偶发失败/网络抖动, 间隔重试一次再报错
        warn "mvn package 首次执行失败, 30 秒后自动重试一次..."
        sleep 30
        if ! mvn "${mvn_args[@]}"; then
            (( patched )) && sed -i 's|<skip>false</skip>|<skip>true</skip>|' "$pom"
            die "后端打包失败, 请检查上方错误日志(可设置 MAVEN_MIRROR 换用其他镜像源后重试)"
        fi
    fi
    (( patched )) && sed -i 's|<skip>false</skip>|<skip>true</skip>|' "$pom"

    local jar="${BACKEND_SRC}/target/jshERP.jar"
    [[ -f "$jar" ]] || die "未生成 ${jar}"
    # 注意: 勿用 '| grep -q' —— pipefail 下 grep -q 提前关闭管道会使 unzip 收到
    # SIGPIPE(退出码141) 而误判失败, 这里先取回列表再判断
    local listing
    listing=$(unzip -l "$jar" 2>/dev/null) || true
    if ! printf '%s\n' "$listing" | grep 'BOOT-INF/classes/com/jsh/erp/ErpApplication.class' >/dev/null; then
        die "jshERP.jar 中缺少主类(编译被跳过), 请确认 pom.xml 后重新打包"
    fi
    ok "后端打包完成: ${jar}"
}

build_web() {
    log "[构建] 编译前端 jshERP-web ..."
    cmd_exists node || die "缺少 Node.js"
    cd "$WEB_SRC" || die "前端目录不存在: $WEB_SRC"

    # npm install 失败会残留不完整 node_modules, 不能只看目录是否存在, 须校验关键包
    if [[ ! -x node_modules/.bin/vue-cli-service ]]; then
        log "安装前端依赖 npm install (使用镜像 ${NPM_REGISTRY})..."
        # vue-cli3/webpack4 老项目依赖树与 npm 7+ 严格 peer 校验冲突(ERESOLVE),
        # 需 --legacy-peer-deps 跳过 peer 自动校验安装
        npm install --registry="$NPM_REGISTRY" --no-audit --no-fund --legacy-peer-deps \
            || die "npm install 失败(若为版本问题, 建议 Node20 + yarn 手动构建后 --skip-build)"
    fi
    if [[ ! -d "${WEB_SRC}/dist" ]]; then
        log "执行 npm run build ..."
        npm run build || die "前端构建失败, 请检查上方日志"
    else
        log "检测到 dist 已存在, 跳过 npm run build"
    fi
    [[ -d "${WEB_SRC}/dist" ]] || die "前端构建未产出 dist 目录"
    ok "前端打包完成: ${WEB_SRC}/dist"
}

# ---------------------------- 4. 组装运行目录 ----------------------------
assemble() {
    log "[部署] 组装运行目录 $INSTALL_DIR ..."
    # 注意: application.yml 中上传/导出路径与 tomcat basedir 是写死的, 需确保存在
    mkdir -p "$BACKEND_HOME"/{bin,config,lib,logs} "$WEB_HOME" "$UPLOAD_DIR" "$EXPORT_DIR" \
             /opt/tmp/tomcat /opt/jshERP/upload /opt/jshERP/export
    chmod -R 755 "$INSTALL_DIR"

    if [[ "$DO_BUILD" == "1" ]]; then
        cp -f "${BACKEND_SRC}/target/jshERP.jar" "$BACKEND_HOME/lib/jshERP.jar"
        cp -f "$BACKEND_SRC"/src/main/resources/application.yml "$BACKEND_HOME/config/"
        cp -f "$BACKEND_SRC"/src/main/resources/logback-spring.xml "$BACKEND_HOME/config/"
        cp -f "$BACKEND_SRC"/src/main/bin/run-manage.sh "$BACKEND_HOME/bin/"
        for f in start stop status restart; do cp -f "$BACKEND_SRC/src/main/bin/${f}.sh" "$BACKEND_HOME/"; done
        chmod +x "$BACKEND_HOME/bin/run-manage.sh" "$BACKEND_HOME"/*.sh

        rm -rf "${WEB_HOME:?}"/*
        cp -rf "$WEB_SRC"/dist/* "$WEB_HOME/"
    fi
    [[ -f "$BACKEND_HOME/lib/jshERP.jar" ]] || die "缺少后端 jar, 请先构建(--skip-build 时需预先放置)"
    [[ -f "$WEB_HOME/index.html" ]] || die "缺少前端静态文件, 请先构建(--skip-build 时需预先放置)"
    ok "程序文件已部署到 $INSTALL_DIR"
}

config_backend() {
    log "[配置] 生成后端 application.yml ..."
    local yml="$BACKEND_HOME/config/application.yml"
    [[ -f "$yml" ]] || die "缺少 $yml"
    local dburl="jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useUnicode=true&characterEncoding=utf8&useCursorFetch=true&defaultFetchSize=500&allowMultiQueries=true&rewriteBatchedStatements=true&useSSL=false"
    local esc_dburl esc_user esc_pass esc_redis_pass
    esc_dburl="$(printf '%s' "$dburl" | sed 's/[&|\\]/\\&/g')"
    esc_user="$(printf '%s' "$DB_USER" | sed 's/[&|\\]/\\&/g')"
    esc_pass="$(printf '%s' "$DB_PASSWORD" | sed 's/[&|\\]/\\&/g')"
    esc_redis_pass="$(printf '%s' "$REDIS_PASSWORD" | sed 's/[&|\\]/\\&/g')"

    sed -i "/^spring:/,/^  redis:/{s|^    url: jdbc:mysql:.*|    url: ${esc_dburl}|; s|^    username: .*|    username: ${esc_user}|; s|^    password: .*|    password: ${esc_pass}|}" "$yml"
    sed -i "/^  redis:/,/^  servlet:/{s|^    password: .*|    password: ${esc_redis_pass}|}" "$yml"

    # 若 Java 版本 > 8, 移除 JDK8 专属的 PermSize 参数, 避免启动报错
    local java_major
    java_major="$("$JAVA_HOME/bin/java" -version 2>&1 | head -1)"
    if [[ "$java_major" != *'"1.'* ]]; then
        sed -i 's/ -XX:PermSize=[0-9]*M -XX:MaxPermSize=[0-9]*M//' "$BACKEND_HOME/bin/run-manage.sh"
        log "检测到非 JDK8($java_major), 已从 run-manage.sh 移除 PermSize 参数"
    fi

    log "关键配置确认:"
    grep -E '^    (url|username|password):' "$yml" | sed 's/^/      /'
    ok "后端配置完成"
}

config_nginx() {
    log "[配置] 生成 Nginx 站点 (端口 ${HTTP_PORT}) ..."
    # 站点内容先写入临时文件, 再按发行版 include 布局放置:
    #   conf.d        —— CentOS/RHEL/Rocky/Alma 及多数发行版默认 include
    #   sites-enabled —— Debian 系默认布局(主配置未 include conf.d 时回退)
    local tmp_conf=/etc/nginx/jshERP.conf.tmp
    cat > "$tmp_conf" <<EOF
server {
    listen       ${HTTP_PORT};
    server_name  ${SERVER_NAME};
    client_max_body_size 20m;
    gzip on;
    gzip_min_length 1k;
    gzip_types text/plain text/css application/json application/javascript application/xml image/svg+xml;

    location / {
        root  ${WEB_HOME};
        index index.html index.htm;
        try_files \$uri \$uri/ /index.html;
    }
    location /jshERP-boot/ {
        proxy_pass http://127.0.0.1:${BACKEND_PORT}/jshERP-boot/;
        proxy_http_version 1.1;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
        proxy_set_header Upgrade \$http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_read_timeout 300s;
    }
}
EOF

    local conf_dest=/etc/nginx/conf.d/jshERP.conf
    if [[ "$(nginx -T 2>&1)" != *"/etc/nginx/conf.d/"* ]]; then
        warn "主配置未 include /etc/nginx/conf.d, 改用 sites-enabled 布局"
        conf_dest=/etc/nginx/sites-available/jshERP.conf
    fi
    mkdir -p "$(dirname "$conf_dest")" /etc/nginx/sites-enabled /etc/nginx/conf.d
    cp -f "$tmp_conf" "$conf_dest"
    rm -f "$tmp_conf"
    if [[ "$conf_dest" == /etc/nginx/sites-available/* ]]; then
        ln -sf "$conf_dest" /etc/nginx/sites-enabled/jshERP.conf
    else
        rm -f /etc/nginx/sites-enabled/jshERP.conf 2>/dev/null || true
    fi

    local err
    err="$(nginx -t 2>&1)" || die "Nginx 配置校验失败, 请检查 ${conf_dest}:\n${err}"
    # 使新站点生效: 运行中则 reload, 未运行则通过服务管理/直接拉起
    if pgrep -x nginx >/dev/null 2>&1; then
        nginx -s reload >/dev/null 2>&1 && ok "Nginx 已重载配置" || warn "nginx reload 失败, 请手动执行 nginx -s reload"
    else
        svc_restart nginx || nginx >/dev/null 2>&1 || warn "nginx 启动失败, 请手动启动"
    fi
    ok "Nginx 站点配置完成: ${conf_dest}"
}

# ---------------------------- SELinux 适配(CentOS/RHEL 系) ----------------------------
# 未处理 SELinux 时, CentOS/RHEL 上典型症状: 前端 403、反代 502、nginx 无法监听自定义端口
fix_selinux() {
    cmd_exists getenforce || return 0
    if [[ "$(getenforce 2>/dev/null)" != "Enforcing" ]]; then
        log "[SELinux] 非 Enforcing 模式, 无需调整"
        return 0
    fi
    log "[SELinux] Enforcing 模式, 自动放行 Nginx 部署所需权限 ..."
    # 1) 允许 Nginx 反向代理访问本机后端端口(否则 502)
    if cmd_exists setsebool; then
        setsebool -P httpd_can_network_connect 1 >/dev/null 2>&1 \
            && ok "httpd_can_network_connect=1 (nginx 反代放行)" \
            || warn "setsebool 失败, 可手动执行: setsebool -P httpd_can_network_connect 1"
    fi
    # 2) 允许 Nginx 监听自定义端口(${HTTP_PORT} 默认不在 http_port_t 内, 否则 bind 失败)
    if ! cmd_exists semanage && [[ "$PM" != "apt-get" ]]; then
        # CentOS 7: policycoreutils-python; CentOS 8+/Rocky/Alma: policycoreutils-python-utils
        PKG_ALLOW_FAIL=1 pkg_install policycoreutils-python-utils >/dev/null 2>&1 \
            || PKG_ALLOW_FAIL=1 pkg_install policycoreutils-python >/dev/null 2>&1 || true
    fi
    if cmd_exists semanage; then
        if ! semanage port -l 2>/dev/null | grep -Eq "^http_port_t[[:space:]]+tcp[[:space:]]+.*\b${HTTP_PORT}\b"; then
            semanage port -a -t http_port_t -p tcp "$HTTP_PORT" >/dev/null 2>&1 \
                && ok "端口 ${HTTP_PORT} 已加入 http_port_t" \
                || warn "semanage port 添加失败, 可手动执行: semanage port -a -t http_port_t -p tcp ${HTTP_PORT}"
        fi
    fi
    # 3) 前端静态目录打上 httpd 可读标签(否则访问 403)
    if [[ -d "$WEB_HOME" ]]; then
        if cmd_exists semanage; then
            semanage fcontext -a -t httpd_sys_content_t "${WEB_HOME}(/.*)?" >/dev/null 2>&1 || true
            restorecon -Rv "$WEB_HOME" >/dev/null 2>&1 || true
        fi
        chcon -Rt httpd_sys_content_t "$WEB_HOME" >/dev/null 2>&1 \
            && ok "WEB 目录已标记 httpd_sys_content_t (nginx 可读)" \
            || warn "chcon 设置 WEB 目录标签失败"
    fi
}

# ---------------------------- 5. 启动与自检 ----------------------------
start_backend() {
    log "[启动] 启动后端服务 ..."
    cd "$BACKEND_HOME" || die "$BACKEND_HOME 不存在"
    ./bin/run-manage.sh stop >/dev/null 2>&1 || true
    ./start.sh
    sleep 2
    local i code
    for i in $(seq 1 60); do
        code="$(curl -s -m 5 -o /dev/null -w '%{http_code}' "http://127.0.0.1:${BACKEND_PORT}/jshERP-boot/" 2>/dev/null || true)"
        if [[ "$code" != "000" && -n "$code" ]]; then ok "后端已就绪(HTTP ${code})"; return 0; fi
        sleep 3
    done
    warn "后端启动自检超时, 最近日志如下:"
    tail -n 30 "$BACKEND_HOME"/logs/*.log 2>/dev/null || true
    # 常见诱因诊断: 后端 @PostConstruct 会写 Redis 缓存, Redis 未就绪会直接启动失败
    if ! ss -ltn 2>/dev/null | grep -q ':6379' \
       && ! netstat -ltn 2>/dev/null | grep -q ':6379'; then
        warn "检测到 Redis(6379) 未监听, 请先启动 Redis 再重启后端:"
        warn "  nohup redis-server /etc/redis.conf >/dev/null 2>&1 &"
        warn "  redis-cli -a '${REDIS_PASSWORD}' ping   # 期望 PONG"
        warn "  cd ${BACKEND_HOME} && ./restart.sh"
    fi
    if ! ss -ltn 2>/dev/null | grep -q ':3306' \
       && ! netstat -ltn 2>/dev/null | grep -q ':3306'; then
        warn "检测到 MySQL/MariaDB(3306) 未监听, 请先启动数据库再重启后端"
    fi
    return 1
}

autostart() {
    [[ "$AUTO_START" != "1" ]] && return 0
    log "[自启] 配置开机自启 ..."
    if systemctl >/dev/null 2>&1; then
        for s in nginx mysql mariadb redis redis-server; do systemctl enable "$s" >/dev/null 2>&1 || true; done
        # 优先使用 systemd 管理后端自启(rc.local 在新版 Debian/Ubuntu 默认不生效)
        cat > /etc/systemd/system/jshERP.service <<EOF
[Unit]
Description=jshERP Backend
After=network.target mysql.service mariadb.service redis.service redis-server.service

[Service]
Type=oneshot
RemainAfterExit=yes
WorkingDirectory=${BACKEND_HOME}
Environment=JAVA_HOME=${JAVA_HOME}
ExecStart=/bin/bash -c 'export JAVA_HOME=${JAVA_HOME}; export PATH=\$JAVA_HOME/bin:\$PATH; cd ${BACKEND_HOME} && ./restart.sh'

[Install]
WantedBy=multi-user.target
EOF
        systemctl daemon-reload >/dev/null 2>&1 || true
        if systemctl enable jshERP >/dev/null 2>&1; then
            ok "已注册 systemd 自启服务 jshERP"
            return 0
        fi
    fi
    # 回退方案: rc.local (systemd 发行版需启用 rc-local.service 开机才会执行)
    [[ -f /etc/rc.local ]] || echo -e '#!/bin/bash\nexit 0' > /etc/rc.local
    if ! grep -q "$BACKEND_HOME" /etc/rc.local; then
        sed -i '/^exit 0/i export JAVA_HOME='"$JAVA_HOME"'\ncd '"$BACKEND_HOME"' && ./restart.sh' /etc/rc.local
    fi
    chmod +x /etc/rc.local
    if systemctl >/dev/null 2>&1; then systemctl enable rc-local >/dev/null 2>&1 || true; fi
    ok "已写入 /etc/rc.local 开机自启"
}

open_firewall() {
    local ip
    ip="$(server_ip)"
    if cmd_exists ufw; then ufw allow "${HTTP_PORT}/tcp" >/dev/null 2>&1 || true
    elif cmd_exists firewall-cmd; then firewall-cmd --permanent --add-port="${HTTP_PORT}/tcp" >/dev/null 2>&1 && firewall-cmd --reload >/dev/null 2>&1 || true; fi
    log "请确认云服务器安全组已放行 TCP ${HTTP_PORT} 端口"
}

summary() {
    local ip code
    ip="$(server_ip)"
    code="$(curl -s -m 5 -o /dev/null -w '%{http_code}' "http://127.0.0.1:${HTTP_PORT}/" 2>/dev/null || true)"
    hr
    if [[ "$code" == "200" ]]; then
        ok "部署成功! 访问地址: http://${ip:-服务器IP}:${HTTP_PORT}/"
    else
        warn "Web 自检返回 ${code:-无响应}, 请检查 Nginx 与后端状态"
    fi
    echo -e "  默认租户账号: jsh       默认密码: 123456"
    echo -e "  默认超管账号: admin     默认密码: 123456"
    echo -e "  数据库: ${DB_NAME} (账号 ${DB_USER}/${DB_PASSWORD})"
    echo -e "  后端目录: ${BACKEND_HOME} (logs 下查看运行日志)"
    echo -e "  停止/重启后端: cd ${BACKEND_HOME} && ./stop.sh / ./restart.sh"
    echo -e "  若需修改数据库/Redis 等配置: ${BACKEND_HOME}/config/application.yml"
    hr
}

# ============================ 主流程 ============================
hr
log "jshERP(管伊佳ERP) 一键部署开始 @ $(date '+%F %T')"
log "系统: ${ID} | 安装目录: ${INSTALL_DIR} | Web端口: ${HTTP_PORT}"
hr

[[ "$(id -u)" -eq 0 ]] || die "请使用 root 运行: sudo bash $0"
[[ -d "$BACKEND_SRC" && -d "$WEB_SRC" ]] || die "请在 jshERP 源码根目录运行本脚本(需包含 jshERP-boot 与 jshERP-web)"
PKG_ALLOW_FAIL=1 pkg_install curl unzip tar wget git ca-certificates >/dev/null 2>&1 || true

if [[ "$DO_DEPS" == "1" ]]; then
    ensure_java8
    ensure_maven
    ensure_node
    ensure_db
    ensure_redis
    ensure_nginx
else
    cmd_exists java || die "--skip-deps 模式下仍需要 Java, 请先配置"
    export JAVA_HOME="${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v java)")")")}"
    export PATH="$JAVA_HOME/bin:$PATH"
    ensure_node   # node 构建参数(NODE_OPTIONS)仍需保证
fi

if [[ "$DO_BUILD" == "1" ]]; then
    ensure_swap   # 低内存机器先补足 swap, 防止构建被内核 OOM killer 杀死
    init_database
    build_backend
    build_web
else
    init_database
fi

assemble
fix_selinux
config_backend
config_nginx
start_backend || die "后端启动失败, 请根据上方日志排查"
autostart
open_firewall
summary
