#!/bin/bash

docker build -t mvn_builder -f mvn_builder.dockerfile .
docker run --rm -v $(pwd)/jshERP-boot:/app/src -v /var/run/docker.sock:/var/run/docker.sock -w /app/src mvn_builder mvn -T 2C clean compile jib:dockerBuild

docker-compose build --parallel
docker-compose up -d
