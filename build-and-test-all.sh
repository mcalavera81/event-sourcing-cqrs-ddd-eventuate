#! /bin/bash

if [ -z "$DOCKER_HOST_IP" ]; then
  echo You must set DOCKER_HOST_IP.
  echo "hostname -I or ifconfig might be of some help"
  exit -1
fi

. ./set-env.sh

#./compile-contracts.sh


./gradlew clean

docker-compose down -v
docker-compose up -d $EXTRA_INFRASTRUCTURE_SERVICES

./gradlew build -x :end-to-end-tests:test $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS

docker-compose build
docker-compose up -d

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082 8083

./gradlew :end-to-end-tests:cleanTest :end-to-end-tests:test $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS

docker-compose down -v

