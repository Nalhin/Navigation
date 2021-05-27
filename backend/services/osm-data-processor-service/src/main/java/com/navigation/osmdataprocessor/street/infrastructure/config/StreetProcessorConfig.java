package com.navigation.osmdataprocessor.street.infrastructure.config;

import com.navigation.osmdataprocessor.street.application.ProcessedStreetPublisher;
import com.navigation.osmdataprocessor.street.application.StreetProcessor;
import com.navigation.osmdataprocessor.street.application.StreetExporter;
import com.navigation.osmdataprocessor.street.domain.StreetConnectionExtractor;
import com.navigation.osmdataprocessor.street.domain.StreetNodeExtractor;
import com.navigation.parser.provider.OSMProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StreetProcessorConfig {
  @Bean
  public StreetProcessor streetProcessor(
      OSMProvider provider, ProcessedStreetPublisher processedStreetPublisher) {
    return new StreetProcessor(
        provider,
        new StreetExporter(
                processedStreetPublisher, new StreetConnectionExtractor(), new StreetNodeExtractor()));
  }
}
