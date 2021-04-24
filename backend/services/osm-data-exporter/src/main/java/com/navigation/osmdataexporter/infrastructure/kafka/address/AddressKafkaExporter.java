package com.navigation.osmdataexporter.infrastructure.kafka.address;

import com.navigation.osmdataexporter.infrastructure.kafka.ExportNotSupportedException;
import com.navigation.parser.elements.*;
import com.navigation.parser.exporter.OSMExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AddressKafkaExporter implements OSMExporter {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final String ADDRESS_TOPIC;

  public AddressKafkaExporter(
      KafkaTemplate<String, Object> kafkaTemplate,
      @Value("${infrastructure.topics.address}") String addressTopic) {
    this.kafkaTemplate = kafkaTemplate;
    this.ADDRESS_TOPIC = addressTopic;
  }

  @Override
  public void export(Node node) {
    var address =
        new AddressDtoBuilder()
            .setCity(node.getTag("addr:city"))
            .setCountry("Poland")
            .setId(node.getId())
            .setHouseNumber(node.getTag("addr:housenumber"))
            .setStreet(node.getTag("addr:street"))
            .setPostCode(node.getTag("addr:postcode"))
            .setLatitude(node.getLatitude())
            .setLongitude(node.getLongitude())
            .createAddressDto();
    kafkaTemplate.send(ADDRESS_TOPIC, String.valueOf(address.getId()), address);
  }

  @Override
  public void export(Way way) {
    throw new ExportNotSupportedException();
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
