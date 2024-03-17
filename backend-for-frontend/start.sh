#!/bin/bash

source ./framework.sh

docker-compose --file docker-compose.yaml up -d

wait_on_health http://localhost:4000 ${PWD##*/}

