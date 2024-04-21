FROM openjdk:latest
ARG TARGETARCH
MAINTAINER yaoyuanming "yaoyuanming0601@gmail.com"

WORKDIR /app

#COPY config/ca/cert.pem /etc/ssl/certs/
#COPY config/ca/key.pem /etc/ssl/private/
#
## Copy the WeChat config directory
#COPY config/wechat /app/config/wechat

COPY target/jshERP.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-Dfile.encoding=utf-8", "-XX:+HeapDumpOnOutOfMemoryError", "-jar", "/app/app.jar"]