#!/bin/bash

echo "------------------------------"
echo "Pushing to docker-registry"
echo "------------------------------"
echo ""

echo "Push docker-image:" $DOCKER_MICROSERVICE_NAME:$MICROSERVICE_VERSION "to" $DOCKER_REGISTRY_HOSTNAME:$DOCKER_REGISTRY_DOCKER_PORT
echo ""

docker start $DOCKER_REGISTRY_CONTAINER_NAME

docker tag $DOCKER_MICROSERVICE_NAME:$MICROSERVICE_VERSION $DOCKER_REGISTRY_HOSTNAME:$DOCKER_REGISTRY_DOCKER_PORT/$DOCKER_MICROSERVICE_NAME:$MICROSERVICE_VERSION

docker push $DOCKER_REGISTRY_HOSTNAME:$DOCKER_REGISTRY_DOCKER_PORT/$DOCKER_MICROSERVICE_NAME:$MICROSERVICE_VERSION
