package com.navigation.osmdataprocessor.address.infrastructure.kafka;

import com.navigation.osmdataprocessor.address.domain.Address;
import com.navigation.osmdataprocessor.address.application.ProcessedAddressPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProcessedAddressKafkaPublisher implements ProcessedAddressPublisher {

  private final String ADDRESS_TOPIC;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public ProcessedAddressKafkaPublisher(
      KafkaTemplate<String, Object> kafkaTemplate,
      @Value("${infrastructure.topics.address}") String addressTopic) {
    this.kafkaTemplate = kafkaTemplate;
    this.ADDRESS_TOPIC = addressTopic;
  }

  @Override
  public void publishProcessedAddress(String id, Address address) {
    kafkaTemplate.send(ADDRESS_TOPIC, id, address);
  }
}
