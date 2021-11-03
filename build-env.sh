export MICROSERVICE_VERSION='0.0.1'

export DOCKER_MICROSERVICE_NAME='fh/microservice'
export DOCKER_CONTAINER_NAME='microservice'

export DOCKER_REGISTRY_HOSTNAME='localhost'
export DOCKER_REGISTRY_CONTAINER_NAME='registry'
export DOCKER_REGISTRY_DOCKER_PORT=5000
export DOCKER_REGISTRY_LOCAL_PORT=5000

echo "--------------"
echo "Build environment" $DOCKER_CONTAINER_NAME
echo "--------------"
echo ""

echo "Docker-image name:" $DOCKER_MICROSERVICE_NAME:$MICROSERVICE_VERSION
echo "Docker-container name:" $DOCKER_CONTAINER_NAME

