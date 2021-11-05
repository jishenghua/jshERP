#!/bin/bash

cd jshERP-boot
mvn clean compile jib:dockerBuild

cd ..
docker-compose build --parallel
docker-compose up -d