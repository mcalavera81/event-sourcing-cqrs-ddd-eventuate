#!/usr/bin/env bash


export EXTRA_INFRASTRUCTURE_SERVICES=cdcservice
export EVENTUATE_LOCAL=yes

if [ -z "$DOCKER_HOST_IP" ] ; then
    if [ -z "$DOCKER_HOST" ] ; then
      export DOCKER_HOST_IP=`hostname`
    else
      echo using ${DOCKER_HOST?}
      XX=${DOCKER_HOST%\:*}
      export DOCKER_HOST_IP=${XX#tcp\:\/\/}
    fi
fi

echo DOCKER_HOST_IP is $DOCKER_HOST_IP

export EVENTUATELOCAL_KAFKA_BOOTSTRAP_SERVERS=$DOCKER_HOST_IP:9092
export EVENTUATELOCAL_CDC_DB_USER_NAME=root
export EVENTUATELOCAL_CDC_DB_PASSWORD=rootpassword
export EVENTUATELOCAL_ZOOKEEPER_CONNECTION_STRING=$DOCKER_HOST_IP:2181

export SPRING_DATASOURCE_URL=jdbc:mysql://${DOCKER_HOST_IP}/eventuate
export SPRING_DATASOURCE_USERNAME=mysqluser
export SPRING_DATASOURCE_PASSWORD=mysqlpw
export SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.jdbc.Driver

BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS="-P eventuateDriver=local"