package com.navigation.osmdataprocessor.address.infrastructure.config;

import com.navigation.osmdataprocessor.address.application.AddressProcessor;
import com.navigation.osmdataprocessor.address.application.AddressExporter;
import com.navigation.parser.provider.OSMProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressProcessorConfig {
  @Bean
  public AddressProcessor addressProcessor(OSMProvider provider, AddressExporter addressExporter) {
    return new AddressProcessor(provider, addressExporter);
  }
}
