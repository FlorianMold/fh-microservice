#!/bin/bash

DIST_FOLDER=dist

dockerfile_flag='false'
use_registry_flag='false'

print_usage() {
  cat <<EOF
    init [OPTIONS]

    Options:
      -h    Print the usage.
      -d    Use the dockerfile for building the docker image.
      -r    Do not store the image in the dist-folder. Used for deploying the image to a registry.
EOF
}

# Change working directory to script directory
BASEDIR=$(dirname "$0")
cd $BASEDIR

while getopts ':dr' flag; do
  case "${flag}" in
    d) dockerfile_flag='true' ;;
    r) use_registry_flag='true' ;;
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

if [[ $use_registry_flag = 'false' ]]; then
  echo "Saving docker-image to" $DIST_FOLDER
  docker save $DOCKER_MICROSERVICE_NAME:$MICROSERVICE_VERSION > $DIST_FOLDER/$DOCKER_CONTAINER_NAME:$MICROSERVICE_VERSION.tar
fi

# Copy helpers
echo "Copy run.sh to" $DIST_FOLDER
cp tools/run.sh $DIST_FOLDER/run.sh
echo "Copy HELP.md to" $DIST_FOLDER
cp tools/HELP.md $DIST_FOLDER/README.md
echo "Copy docker-compose.yml to" $DIST_FOLDER
cp tools/docker-compose.yml $DIST_FOLDER/docker-compose.yml
cp tools/docker-compose.db.yml $DIST_FOLDER/docker-compose.db.yml
echo "Copy .env to" $DIST_FOLDER
cp tools/.env $DIST_FOLDER/.env
