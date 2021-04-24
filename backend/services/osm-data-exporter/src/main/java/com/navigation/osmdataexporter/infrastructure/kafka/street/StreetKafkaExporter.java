package com.navigation.osmdataexporter.infrastructure.kafka.street;

import com.navigation.osmdataexporter.infrastructure.kafka.ExportNotSupportedException;
import com.navigation.parser.elements.*;
import com.navigation.parser.exporter.OSMExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StreetKafkaExporter implements OSMExporter {
  private final String STREET_CONNECTIONS_TOPIC;
  private final String STREET_NODES_TOPIC;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public StreetKafkaExporter(
      KafkaTemplate<String, Object> kafkaTemplate,
      @Value("${infrastructure.topics.street-connections}") String streetWaysTopic,
      @Value("${infrastructure.topics.street-nodes}") String streetNodesTopic) {
    this.kafkaTemplate = kafkaTemplate;
    STREET_CONNECTIONS_TOPIC = streetWaysTopic;
    STREET_NODES_TOPIC = streetNodesTopic;
  }

  @Override
  public void export(Node node) {
    var streetNode = new StreetNodeDto(node.getLatitude(), node.getLongitude(), node.getId());
    kafkaTemplate.send(STREET_NODES_TOPIC, String.valueOf(node.getId()), streetNode);
  }

  @Override
  public void export(Way way) {
    List<Long> nodeReferences = way.getNodeReferences();
    for (int i = 1; i < nodeReferences.size(); i++) {
      long from = nodeReferences.get(i - 1);
      long to = nodeReferences.get(i);
      kafkaTemplate.send(
          STREET_CONNECTIONS_TOPIC,
          String.valueOf(from) + to,
          new StreetConnectionDto(from, to, Integer.parseInt(way.getTag("maxspeed"))));
    }
    if (!way.getTag("oneway").equals("yes")) {
      for (int i = nodeReferences.size() - 2; i >= 0; i--) {
        long from = nodeReferences.get(i + 1);
        long to = nodeReferences.get(i);
        kafkaTemplate.send(
            STREET_CONNECTIONS_TOPIC,
            String.valueOf(from) + to,
            new StreetConnectionDto(from, to, Integer.parseInt(way.getTag("maxspeed"))));
      }
    }
  }

  @Override
  public void export(Bounds bounds) {
    throw new ExportNotSupportedException();
  }

  @Override
  public void export(Metadata metadata) {
    throw new ExportNotSupportedException();
  }

  @Override
  public void export(Relation relation) {
    throw new ExportNotSupportedException();
  }
}
