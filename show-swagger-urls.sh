#! /bin/bash -e

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082 8083

echo The microservices are running
echo You can visit these URLS
echo http://${DOCKER_HOST_IP?}:8081/swagger-ui.html - Catalog Command Service
echo http://${DOCKER_HOST_IP?}:8082/swagger-ui.html - Order History Service
echo http://${DOCKER_HOST_IP?}:8083/swagger-ui.html - Order Command Service
