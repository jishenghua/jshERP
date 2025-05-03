#!/bin/bash

SERVER_NAME=jshERP
readonly APP_HOME=${FILE_PATH:-$(dirname $(cd `dirname $0`; pwd))}
#readonly JAVA_HOME=""
readonly CONFIG_HOME="$APP_HOME/config/"  # 配置文件目录
readonly LIB_HOME="$APP_HOME/lib"         # JAR包目录
readonly LOGS_HOME="$APP_HOME/logs"       # 日志目录
readonly PID_FILE="$LOGS_HOME/application.pid"  # PID 文件路径
readonly APP_MAIN_CLASS="jshERP.jar"      # 主程序 JAR 包名
readonly LOG_CONFIG="$CONFIG_HOME/logback-spring.xml"  # logback 配置路径
readonly JAVA_RUN="-Dlogs.home=$LOGS_HOME -Dlogging.config=$LOG_CONFIG -Dspring.config.location=file:$CONFIG_HOME -Dspring.pid.file=$PID_FILE -Dspring.pid.fail-on-write-error=true"
readonly JAVA_OPTS="-server -Xms128m -Xmx320m -XX:PermSize=128M -XX:MaxPermSize=256M $JAVA_RUN"  # JVM 启动参数
readonly JAVA="java"
PID=0

# 如果 logs 目录不存在，则创建
if [ ! -x "$LOGS_HOME" ]
then
  mkdir $LOGS_HOME
fi

# 为 JAVA 执行文件添加执行权限（预留的）
chmod +x -R "$JAVA_HOME/bin/"

# 加载函数库（如果存在）
functions="/etc/functions.sh"
if test -f $functions ; then
  . $functions
else
  # 如果没有函数库，则自己定义 success/failure/warning 函数
  success()
  {
    echo " SUCCESS! $@"
  }
  failure()
  {
    echo " ERROR! $@"
  }
  warning()
  {
    echo "WARNING! $@"
  }
fi

# 检查是否已启动，获取 PID
function checkpid() {
   PID=$(ps -ef | grep $APP_MAIN_CLASS | grep -v 'grep' | awk '{print int($2)}')
    if [[ -n "$PID" ]]
    then
      return 0
    else
      return 1
    fi
}

# 启动函数
function start() {
   checkpid
   if [[ $? -eq 0 ]]
   then
      warning "[$APP_MAIN_CLASS]: 已经启动！(PID=$PID)"
   else
      echo -n "[$APP_MAIN_CLASS]: 启动中 ..."
      JAVA_CMD="nohup $JAVA $JAVA_OPTS -jar $LIB_HOME/$APP_MAIN_CLASS > /dev/null 2>&1 &"
      # echo "执行命令 : $JAVA_CMD"
      sh -c "$JAVA_CMD"
      sleep 3
      checkpid
      if [[ $? -eq 0 ]]
      then
         success "(PID=$PID) "
      else
         failure "启动失败"
      fi
   fi
}

# 停止函数
function stop() {
   checkpid
   if [[ $? -eq 0 ]];
   then
      echo -n "[$APP_MAIN_CLASS]: 正在停止 ...(PID=$PID) "
      kill -9 $PID
      if [[ $? -eq 0 ]];
      then
         echo 0 > $PID_FILE
         success "停止成功"
      else
         failure "停止失败"
      fi
   else
      warning "[$APP_MAIN_CLASS]: 未在运行中 ..."
   fi
}

# 查看运行状态
function status() {
   checkpid
   if [[ $? -eq 0 ]]
   then
      success "[$APP_MAIN_CLASS]: 正在运行中！(PID=$PID)"
      return 0
   else
      failure "[$APP_MAIN_CLASS]: 未运行"
      return 1
   fi
}

# 显示系统和运行信息
function info() {
   echo "系统信息："
   echo
   echo "****************************"
   echo `head -n 1 /etc/issue`
   echo `uname -a`
   echo
   echo "JAVA_HOME=$JAVA_HOME"
   echo
   echo "Java 环境信息："
   echo `$JAVA -version`
   echo
   echo "APP_HOME=$APP_HOME"
   echo "APP_MAIN_CLASS=$APP_MAIN_CLASS"
   echo
   echo "****************************"
}

# 命令分支入口
case "$1" in
   'start')
      start
      ;;
   'stop')
     stop
     ;;
   'restart')
     stop
     start
     ;;
   'status')
     status
     ;;
   'info')
     info
     ;;
    *)
     echo "用法: $0 {help|start|stop|restart|status|info}"
     ;;
esac
exit 0
