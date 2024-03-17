#!/bin/bash

# List of service names and their docker-compose files
services=(
   "bff:docker-compose.yaml"

)
container_ids=()

start_service() {
    local service_name=$1
    local compose_file=$2
    echo "Starting service $service_name..."
    docker compose -f $compose_file up  -d
}

# Loop to start all services
for service in "${services[@]}"; do
    IFS=':' read -ra service_info <<< "$service"
    service_name=${service_info[0]}
    compose_file=${service_info[1]}

    start_service "$service_name" "$compose_file"
done


# Function to display real-time logs
 {
    for service in "${services[@]}"; do
        IFS=':' read -ra service_info <<< "$service"
        service_name=${service_info[0]}
        compose_file=${service_info[1]}

        echo "Displaying logs for service $service_name"
         docker compose -f $compose_file logs -f |
          grep -E -v 'RouterExplorer|InstanceLoader|NestFactory|NestApplication|RoutesResolver|Controller' &
    done
    wait
}

show_logs()