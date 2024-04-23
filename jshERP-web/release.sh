#!/bin/bash
set -x
# 进入指定目录
cd /root/release/jshERP

# 更新代码
git fetch --all && git reset --hard origin/master

cd ./jshERP-web

# 更新与构建项目
yarn install
yarn build

# 构建 Docker 镜像
tag=$(date "+%Y%m%d%H%M")
docker build -f Dockerfile -t release-erp-demo:$tag .

# 删除旧容器并启动新容器
docker stop release-erp-demo && sudo docker rm release-erp-demo
docker run -p 84:84 -d --name release-erp-demo --network=release-network release-erp-demo:$tag
