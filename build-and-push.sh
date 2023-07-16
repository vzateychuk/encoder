#! /usr/bin/bash

./gradlew clean bootJar
docker login

cd ./signer-srv || exit
docker build -t vzateychuk/signer-service:0.0.1 ./
docker push vzateychuk/signer-service:0.0.1
