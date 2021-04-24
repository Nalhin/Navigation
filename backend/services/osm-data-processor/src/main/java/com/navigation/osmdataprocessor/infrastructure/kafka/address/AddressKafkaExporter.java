package com.navigation.osmdataprocessor.infrastructure.kafka.address;

import com.navigation.osmdataprocessor.domain.address.Address;
import com.navigation.osmdataprocessor.domain.address.ProcessedAddressExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AddressKafkaExporter implements ProcessedAddressExporter {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final String ADDRESS_TOPIC;

  public AddressKafkaExporter(
      KafkaTemplate<String, Object> kafkaTemplate,
      @Value("${infrastructure.topics.address}") String addressTopic) {
    this.kafkaTemplate = kafkaTemplate;
    this.ADDRESS_TOPIC = addressTopic;
  }

  @Override
  public void exportProcessedAddress(String id, Address address) {
    kafkaTemplate.send(ADDRESS_TOPIC, String.valueOf(address.getId()), address);
  }
}
