#!/bin/bash

# cd到AwsomeChat目录
cd /root/erp/jshERP/jshERP-boot

# 更新代码
# git fetch --all && git reset --hard origin/master

# 清除 Maven 编译结果
sudo mvn clean

# 编译 Maven 项目
sudo mvn package -DskipTests

# 获取当前时间作为 Docker 镜像 tag
tag=$(date "+%Y%m%d%H%M")

# 构建 Docker 镜像
sudo docker build -t erp-demo:$tag .

# 停止并删除名称为 awsomechat 的容器
#sudo docker ps -a | grep -q awsomechat && sudo docker stop release-ninefactor && sudo docker rm release-ninefactor
if sudo docker ps -a | grep -q erp-demo; then
  sudo docker stop erp-demo
  sudo docker rm erp-demo
fi

# 运行 Docker 容器
sudo docker run \
  -p 83:8080 \
  -e DB_IP=theninefactor-database.mysql.database.azure.com \
  -e DB_PORT=3306 \
  -e DB_NAME=jsh_erp \
  -e REDIS_HOST=release-redis \
  -e DB_USERNAME=theninefactor \
  -e DB_PASSWORD=pYWOx7pJIe^EmRSBa7 \
  --network=release-network \
  -d --name erp-demo erp-demo:$tag
