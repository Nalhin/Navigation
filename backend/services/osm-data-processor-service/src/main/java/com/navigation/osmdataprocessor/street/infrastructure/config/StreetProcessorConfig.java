package com.navigation.osmdataprocessor.street.infrastructure.config;

import com.navigation.osmdataprocessor.street.application.StreetProcessor;
import com.navigation.osmdataprocessor.street.application.StreetExporter;
import com.navigation.parser.provider.OSMProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StreetProcessorConfig {
  @Bean
  public StreetProcessor streetProcessor(OSMProvider provider, StreetExporter streetExporter) {
    return new StreetProcessor(provider, streetExporter);
  }
}
