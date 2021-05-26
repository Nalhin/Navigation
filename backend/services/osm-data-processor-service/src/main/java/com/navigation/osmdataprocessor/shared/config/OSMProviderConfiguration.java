package com.navigation.osmdataprocessor.shared.config;

import com.navigation.parser.provider.OSMProvider;
import com.navigation.parser.provider.OSMProviderBzipFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSMProviderConfiguration {

  @Bean
  public OSMProvider OSMProvider(@Value("${infrastructure.osm.filePath}") String filePath) {
    return new OSMProviderBzipFile(filePath);
  }
}
