FROM centos:7

RUN yum update -y && yum install telnet unzip java-1.8.0-openjdk -y

RUN mkdir /opt/jshERP

WORKDIR /opt/jshERP

COPY boot/* ./

ENTRYPOINT java -server -Xms128m -Xmx320m -jar jshERP.jar --spring.config.location=/opt/jshERP/application.properties

# docker build -t beer5214/jsherp .
