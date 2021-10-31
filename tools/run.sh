#!/bin/bash

MICROSERVICE_VERSION='0.0.1'
DOCKER_CONTAINER_NAME='microservice'

postgres_flag=''

print_usage() {
  cat <<EOF
    init [OPTIONS]

    Options:
      -p    Build a docker image from the current sources
EOF
}

# Change working directory to script directory
BASEDIR=$(dirname "$0")
cd $BASEDIR

while getopts ':p' flag; do
  case "${flag}" in
    p) postgres_flag='true' ;;
    *) print_usage
       exit 1 ;;
  esac
done

# Load containers
docker load --input $DOCKER_CONTAINER_NAME:$MICROSERVICE_VERSION.tar

# Run containers
if [[ $postgres_flag = 'true' ]]; then
  echo "Running postgres-db and microservice"
  docker-compose -f docker-compose.db.yml -f docker-compose.yml down
  docker-compose -f docker-compose.db.yml -f docker-compose.yml up
  exit 1
fi

echo "Running microservice with integrated database"
docker-compose down
docker-compose up
