#!/bin/bash

docker build -t mvn_builder -f mvn_builder.dockerfile .
docker run --rm -v $(pwd)/jshERP-boot:/app/src -v $(pwd)/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock -v /var/lib/docker:/var/lib/docker -w /app/src mvn_builder mvn -T 2C clean compile jib:dockerBuild

docker-compose build --parallel
docker-compose up -d
