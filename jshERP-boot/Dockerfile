# 使用 Java 1.8 作为基础镜像
FROM openjdk:8-jdk

# 设置工作目录
WORKDIR /app

# 设置项目维护者
MAINTAINER yaoyuanming "yaoyuanming0601@gmail.com"

# 复制应用程序 JAR 到工作目录
COPY target/jshERP.jar /app/jshERP.jar

# 设置配置和其他路径
ENV SERVER_NAME="jshERP"
ENV APP_HOME="/app"
ENV CONFIG_HOME="/app/config"
ENV LIB_HOME="/app/lib"
ENV LOGS_HOME="/app/logs"
ENV PID_FILE="/app/logs/application.pid"
ENV APP_MAIN_CLASS="jshERP.jar"
ENV LOG_CONFIG="/app/config/logback-spring.xml"

# 创建日志目录
RUN mkdir -p /app/logs

# 确保 JAR 可执行
RUN chmod +x /app/jshERP.jar

# 暴露应用程序的端口
EXPOSE 8080

# 设置启动命令，包含 JAVA_OPTS 和 JAVA_RUN
ENV JAVA_OPTS="-server -Xms128m -Xmx320m -XX:PermSize=128M -XX:MaxPermSize=256M"
ENV JAVA_RUN="-Dlogs.home=/app/logs -Dlogging.config=$LOG_CONFIG -Dspring.config.location=file:/app/config -Dspring.pid.file=$PID_FILE -Dspring.pid.fail-on-write-error=true"

# 运行应用程序，使用 JAVA_OPTS 和 JAVA_RUN
CMD java $JAVA_OPTS $JAVA_RUN -jar /app/jshERP.jar
