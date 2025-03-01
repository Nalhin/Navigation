docker-compose -f docker-compose.dev.yml exec kafka kafka-topics --create --if-not-exists --zookeeper zookeeper:2181 --partitions 1 --replication-factor 1 --topic exporter.address
docker-compose -f docker-compose.dev.yml exec kafka kafka-topics --create --if-not-exists --zookeeper zookeeper:2181 --partitions 1 --replication-factor 1 --topic exporter.street-connections
docker-compose -f docker-compose.dev.yml exec kafka kafka-topics --create --if-not-exists --zookeeper zookeeper:2181 --partitions 1 --replication-factor 1 --topic exporter.street-nodes

curl -X DELETE http://localhost:8090/connectors/address-es
curl -X DELETE http://localhost:8090/connectors/address-mongo
curl -X DELETE http://localhost:8090/connectors/street-nodes
curl -X DELETE http://localhost:8090/connectors/street-connections

curl -X POST http://localhost:8090/connectors \
  -H 'Content-Type:application/json' \
  -H 'Accept:application/json' \
  -d @./connectors/address-es.sink.json

curl -X POST http://localhost:8090/connectors \
  -H 'Content-Type:application/json' \
  -H 'Accept:application/json' \
  -d @./connectors/address-mongo.sink.json

curl -X POST http://localhost:8090/connectors \
  -H 'Content-Type:application/json' \
  -H 'Accept:application/json' \
  -d @./connectors/street-nodes.sink.json

curl -X POST http://localhost:8090/connectors \
  -H 'Content-Type:application/json' \
  -H 'Accept:application/json' \
  -d @./connectors/street-connections.sink.json
