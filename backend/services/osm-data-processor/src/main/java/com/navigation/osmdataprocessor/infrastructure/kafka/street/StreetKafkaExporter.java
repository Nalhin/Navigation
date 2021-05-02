package com.navigation.osmdataprocessor.infrastructure.kafka.street;

import com.navigation.osmdataprocessor.domain.street.ProcessedStreetExporter;
import com.navigation.osmdataprocessor.domain.street.StreetConnection;
import com.navigation.osmdataprocessor.domain.street.StreetNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StreetKafkaExporter implements ProcessedStreetExporter {
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
  public void exportProcessedStreetConnection(String id, StreetConnection streetConnection) {
    kafkaTemplate.send(STREET_CONNECTIONS_TOPIC, id, streetConnection);
  }

  @Override
  public void exportProcessedStreetNode(String id, StreetNode streetNode) {
    kafkaTemplate.send(STREET_NODES_TOPIC, id, streetNode);
  }
}
