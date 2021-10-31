#!/bin/bash

DIST_FOLDER=dist
DOCKER_MICROSERVICE_VERSION='0.0.1'
DOCKER_MICROSERVICE_NAME='docker.io/fh/microservice'
DOCKER_CONTAINER_NAME='microservice'

dockerfile_flag='false'

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

while getopts ':d' flag; do
  case "${flag}" in
    d) dockerfile_flag='true' ;;
    *) print_usage
       exit 1 ;;
  esac
done

# Cleanup & create directory structure
rm -rf $DIST_FOLDER
mkdir $DIST_FOLDER

# Build container
if [[ $dockerfile_flag = 'false' ]]; then
./docker/buildpack.sh
fi

if [[ $dockerfile_flag = 'true' ]]; then
./docker/build.sh
fi

docker save $DOCKER_MICROSERVICE_NAME:$DOCKER_MICROSERVICE_VERSION > $DIST_FOLDER/$DOCKER_CONTAINER_NAME:$DOCKER_MICROSERVICE_VERSION.tar

# Copy helpers
cp tools/run.sh $DIST_FOLDER/run.sh
cp tools/HELP.md $DIST_FOLDER/README.md
cp tools/docker-compose.yml $DIST_FOLDER/docker-compose.yml
cp tools/docker-compose.db.yml $DIST_FOLDER/docker-compose.db.yml
cp tools/.env $DIST_FOLDER/.env
