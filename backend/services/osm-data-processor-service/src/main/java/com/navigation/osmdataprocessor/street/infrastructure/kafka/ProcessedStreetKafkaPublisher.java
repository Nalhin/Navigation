package com.navigation.osmdataprocessor.street.infrastructure.kafka;

import com.navigation.osmdataprocessor.street.application.ProcessedStreetPublisher;
import com.navigation.osmdataprocessor.street.domain.StreetConnection;
import com.navigation.osmdataprocessor.street.domain.StreetNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProcessedStreetKafkaPublisher implements ProcessedStreetPublisher {
  private final String STREET_CONNECTIONS_TOPIC;
  private final String STREET_NODES_TOPIC;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public ProcessedStreetKafkaPublisher(
      KafkaTemplate<String, Object> kafkaTemplate,
      @Value("${infrastructure.topics.street-connections}") String streetWaysTopic,
      @Value("${infrastructure.topics.street-nodes}") String streetNodesTopic) {
    this.kafkaTemplate = kafkaTemplate;
    STREET_CONNECTIONS_TOPIC = streetWaysTopic;
    STREET_NODES_TOPIC = streetNodesTopic;
  }

  @Override
  public void publishProcessedStreetConnection(String id, StreetConnection streetConnection) {
    kafkaTemplate.send(STREET_CONNECTIONS_TOPIC, id, streetConnection);
  }

  @Override
  public void publishProcessedStreetNode(String id, StreetNode streetNode) {
    kafkaTemplate.send(STREET_NODES_TOPIC, id, streetNode);
  }
}
