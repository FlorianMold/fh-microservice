#!/bin/bash

MICROSERVICE_VERSION='0.0.1'
DOCKER_CONTAINER_NAME='microservice'

postgres_flag='false'
use_registry_flag='false'

print_usage() {
  cat <<EOF
    init [OPTIONS]

    Options:
      -h    Print the usage.
      -p    Use a postgres database for the microservice.
      -r    Load the image from a repository and not from the filesystem.
EOF
}

# Change working directory to script directory
BASEDIR=$(dirname "$0")
cd $BASEDIR

while getopts ':pr' flag; do
  case "${flag}" in
    p) postgres_flag='true' ;;
    r) use_registry_flag='true' ;;
    *) print_usage
       exit 1 ;;
  esac
done

FILE=./.env
if [ ! -f "$FILE" ]; then
    echo "ERR: $FILE does not exist, but is needed for running the application"
    exit 1
fi

# Load containers
if [[ $use_registry_flag = 'false' ]]; then
  echo "Loading docker-image from file-system"
  docker load --input $DOCKER_CONTAINER_NAME:$MICROSERVICE_VERSION.tar
fi

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
