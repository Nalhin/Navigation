package com.navigation.osmdataprocessor.address.infrastructure.kafka;

import com.navigation.osmdataprocessor.address.domain.Address;
import com.navigation.osmdataprocessor.address.application.ProcessedAddressSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProcessedAddressKafkaSender implements ProcessedAddressSender {

  private final String ADDRESS_TOPIC;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public ProcessedAddressKafkaSender(
      KafkaTemplate<String, Object> kafkaTemplate,
      @Value("${infrastructure.topics.address}") String addressTopic) {
    this.kafkaTemplate = kafkaTemplate;
    this.ADDRESS_TOPIC = addressTopic;
  }

  @Override
  public void sendProcessedAddress(String id, Address address) {
    kafkaTemplate.send(ADDRESS_TOPIC, id, address);
  }
}
