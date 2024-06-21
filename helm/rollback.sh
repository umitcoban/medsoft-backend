#!/bin/bash


charts=(
  "rabbitmq"
  "prometheus"
  "kibana"
  "elasticsearch"
  "logstash"
  "grafana "
  "key-cloak-db"
  "keycloak"
  "account-db"
  "account-server"
  "config-server"
  "document-mongo-db"
  "document-server"
  "eureka-server"
  "gateway-server"
)

for chart in "${charts[@]}"; do
  name=$(echo $chart | awk '{print $1}')

  helm uninstall $name

done

echo "All charts uninstalled successfully."
