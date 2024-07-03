#!/bin/bash


charts=(
  "rabbitmq ./rabbitmq"
  "prometheus ./prometheus"
  "kibana ./kibana"
  "elasticsearch ./elasticsearch"
  "logstash ./logstash"
  "grafana ./grafana"
  "key-cloak-db ./key-cloak-db"
  "keycloak ./keycloak"
  "account-db ./account-db"
  "account-server ./account-server"
  "config-server ./config-server"
  "document-mongo-db ./document-mongo-db"
  "document-server ./document-server"
  "eureka-server ./eureka-server"
  "gateway-server ./gateway-server"
)

for chart in "${charts[@]}"; do
  name=$(echo $chart | awk '{print $1}')
  path=$(echo $chart | awk '{print $2}')

  helm install $name $path

  if [ $? -ne 0 ]; then
    echo "Failed to install $name"
    exit 1
  fi
done

echo "All charts installed successfully."
