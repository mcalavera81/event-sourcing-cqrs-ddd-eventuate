#! /bin/bash -e

if [ -z "$DOCKER_HOST_IP" ]; then
  echo You must set DOCKER_HOST_IP.
  echo "hostname -I or ifconfig might be of some help"
  exit -1
fi

. ./set-env.sh

./gradlew clean
./gradlew assemble $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS

docker-compose down -v
docker-compose up -d $EXTRA_INFRASTRUCTURE_SERVICES

docker-compose build
docker-compose up -d

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082 8083

./show-swagger-urls.sh
