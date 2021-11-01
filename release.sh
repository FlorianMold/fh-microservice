#!/bin/bash

docker start registry

docker tag fh/microservice:0.0.1 localhost:5000/fh/microservice:0.0.1

docker push localhost:5000/fh/microservice:0.0.1
