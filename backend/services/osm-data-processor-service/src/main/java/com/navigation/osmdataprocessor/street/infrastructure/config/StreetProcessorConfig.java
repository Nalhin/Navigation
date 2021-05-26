package com.navigation.osmdataprocessor.street.infrastructure.config;

import com.navigation.osmdataprocessor.street.application.ProcessedStreetSender;
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
      OSMProvider provider, ProcessedStreetSender processedStreetSender) {
    return new StreetProcessor(
        provider,
        new StreetExporter(
            processedStreetSender, new StreetConnectionExtractor(), new StreetNodeExtractor()));
  }
}
