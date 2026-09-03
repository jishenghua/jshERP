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

cmd_exists() { command -v "$1" >/dev/null 2>&1; }

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

pkg_install() {
    local missing=() p
    for p in "$@"; do
        cmd_exists "$p" || missing+=("$p")
    done
    [[ ${#missing[@]} -eq 0 ]] && return 0
    log "安装缺失依赖: ${missing[*]}"
    if [[ "$PM" == "apt-get" ]]; then apt-get update >/dev/null 2>&1 || true; fi
    if ! "${PM_INSTALL[@]}" "${missing[@]}"; then
        if [[ "${PKG_ALLOW_FAIL:-0}" == "1" ]]; then
            warn "安装 ${missing[*]} 失败(已容错, 由调用方决定回退)"
            return 1
        fi
        die "安装 ${missing[*]} 失败，请检查网络/软件源后重试"
    fi
}

ensure_epel() { [[ "$PM" == "apt-get" ]] && return 0; $PM install -y epel-release >/dev/null 2>&1 || true; }

svc_restart() {
    local s
    for s in "$@"; do
        if systemctl >/dev/null 2>&1; then
            if systemctl list-unit-files 2>/dev/null | grep "^${s}\.service" >/dev/null; then
                systemctl enable "${s}" >/dev/null 2>&1 || true
                systemctl restart "${s}" && { ok "服务 ${s} 已重启"; return 0; }
            fi
        else
            service "${s}" restart 2>/dev/null && { ok "服务 ${s} 已重启"; return 0; }
        fi
    done
    warn "无法自动管理服务($*) , 请手动启动"
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
                pkg_install openjdk-8-jdk || true
            fi
        else
            pkg_install java-1.8.0-openjdk-devel || true
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
        pkg_install maven || true
        if ! cmd_exists mvn; then
            log "下载 Maven 3.9.x 到 /opt/maven ..."
            mkdir -p /opt/maven
            curl -fL --connect-timeout 20 -o /tmp/maven.tar.gz \
                "https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz" \
                || die "下载 Maven 失败，请手动安装后重试(--skip-deps 可跳过)"
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
        pkg_install nodejs npm || true
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
    export NODE_OPTIONS="${NODE_OPTIONS:-} --max_old_space_size=4096"
    ok "Node 就绪: $(node -v) / npm $(npm -v 2>/dev/null)"
}

ensure_db() {
    log "[环境] 检测 MySQL/MariaDB ..."
    if cmd_exists mysql; then
        ok "检测到 MySQL 客户端: $(mysql --version)"
    else
        if [[ "$PM" == "apt-get" ]]; then
            # Ubuntu 的 mysql-server 为真实 MySQL8;
            # Debian 中 mysql-server 是虚拟包(实际由 mariadb-server 提供, 无安装候选),
            # 故用 apt-cache policy 判断是否存在真实候选, 失败时自动回退 MariaDB
            if apt-cache policy mysql-server 2>/dev/null | grep -E '^[[:space:]]*Candidate: [0-9]' >/dev/null; then
                PKG_ALLOW_FAIL=1 pkg_install mysql-server || pkg_install mariadb-server
            else
                log "发行版无 mysql-server 候选, 安装 mariadb-server(兼容本系统 SQL)"
                pkg_install mariadb-server
            fi
        else
            PKG_ALLOW_FAIL=1 pkg_install mysql-server || { ensure_epel; pkg_install mariadb-server; }
        fi
    fi
    # 启动(新装系统上 mysql/mariadb 服务名不同)
    svc_restart mysql mariadb || svc_restart mysqld mariadbd
    local i
    for i in $(seq 1 30); do
        if mysql -uroot -e "SELECT 1" >/dev/null 2>&1; then break; fi
        sleep 2
        [[ "$i" -eq 30 ]] && warn "数据库服务等待超时, 请手动检查(mysql -uroot 是否可登录)"
    done
    ok "MySQL/MariaDB 服务可用"
}

ensure_redis() {
    log "[环境] 检测 Redis ..."
    if ! cmd_exists redis-server && ! cmd_exists redis-cli; then
        if [[ "$PM" == "apt-get" ]]; then
            if apt-cache show redis-server >/dev/null 2>&1; then pkg_install redis-server
            else pkg_install redis; fi
        else
            ensure_epel
            pkg_install redis
        fi
    fi
    svc_restart redis-server redis
    local rc=""
    for f in /etc/redis/redis.conf /etc/redis.conf; do [[ -f "$f" ]] && rc="$f" && break; done
    if [[ -n "$rc" ]]; then
        sed -i "s/^#\? *requirepass .*/requirepass ${REDIS_PASSWORD}/" "$rc"
        grep -q "^requirepass " "$rc" || echo "requirepass ${REDIS_PASSWORD}" >> "$rc"
        ok "Redis 密码已写入 $rc"
        svc_restart redis-server redis
    else
        warn "未找到 Redis 配置文件, 请手动设置 requirepass ${REDIS_PASSWORD}"
    fi
}

ensure_nginx() {
    log "[环境] 检测 Nginx ..."
    if ! cmd_exists nginx; then
        ensure_epel
        pkg_install nginx
    fi
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
    ok "数据库 ${DB_NAME} 与账号 ${DB_USER} 已就绪"

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
    cat > /etc/nginx/conf.d/jshERP.conf <<EOF
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
    nginx -t || die "Nginx 配置校验失败, 请检查 /etc/nginx/conf.d/jshERP.conf"
    svc_restart nginx || nginx -s reload
    ok "Nginx 配置完成"
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
    # 回退方案: rc.local
    [[ -f /etc/rc.local ]] || echo -e '#!/bin/bash\nexit 0' > /etc/rc.local
    if ! grep -q "$BACKEND_HOME" /etc/rc.local; then
        sed -i '/^exit 0/i export JAVA_HOME='"$JAVA_HOME"'\ncd '"$BACKEND_HOME"' && ./restart.sh' /etc/rc.local
    fi
    chmod +x /etc/rc.local
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
pkg_install curl unzip tar wget git >/dev/null 2>&1 || true

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
    init_database
    build_backend
    build_web
else
    init_database
fi

assemble
config_backend
config_nginx
start_backend || die "后端启动失败, 请根据上方日志排查"
autostart
open_firewall
summary
