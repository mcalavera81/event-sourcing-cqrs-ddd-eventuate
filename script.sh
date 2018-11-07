#!/usr/bin/env bash

. ./set-env.sh

./_build-and-test-all.sh -f docker-compose-eventuate-local-mysql.yml $BUILD_AND_TEST_ALL_EVENTUATE_LOCAL_EXTRA_COMPOSE_ARGS $* -P eventuateDriver=local
