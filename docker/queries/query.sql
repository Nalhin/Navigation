CREATE
STREAM street_connections(fromId BIGINT, toId BIGINT, maxSpeed INT) WITH (
    kafka_topic = 'exporter.street-connections',
    value_format =' JSON'
);

CREATE
STREAM street_nodes(latitude DOUBLE, longitude DOUBLE, id BIGINT) WITH (
    kafka_topic = 'exporter.street-nodes',
    value_format = 'JSON'
);

CREATE
STREAM transformed_street_connections_one_way WITH (
    kafka_topic = 'transformed.street-connections-one-way',
    value_format = 'JSON'
) AS
SELECT connections.maxSpeed,
       connections.toId    AS toId,
       from_node.latitude  AS fromLatitude,
       from_node.longitude AS fromLongitude,
       from_node.Id        AS fromId
FROM street_connections connections
         INNER JOIN street_nodes from_node WITHIN 7 DAYS
ON connections.fromId = from_node.id EMIT CHANGES;

CREATE
STREAM transformed_street_connections WITH (
    kafka_topic = 'transformed.street-connections',
    value_format = 'JSON'
) AS
SELECT connections.maxSpeed,
       connections.fromLatitude,
       connections.fromLongitude,
       connections.fromId,
       to_node.latitude  AS toLatitude,
       to_node.longitude AS toLongitude,
       to_node.Id        AS toId
FROM transformed_street_connections_one_way connections
         INNER JOIN street_nodes to_node WITHIN 7 DAYS
ON connections.toId = to_node.id EMIT CHANGES;
