package com.navigation.osmdataexporter.infrastructure.export;

import com.navigation.osmdataexporter.domain.address.AddressExporter;
import com.navigation.osmdataexporter.domain.street.StreetExporter;
import com.navigation.osmdataexporter.infrastructure.kafka.address.AddressKafkaExporter;
import com.navigation.osmdataexporter.infrastructure.kafka.street.StreetKafkaExporter;
import com.navigation.parser.provider.OSMProvider;
import com.navigation.parser.provider.OSMProviderBzipFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MainExporter implements ApplicationListener<ApplicationReadyEvent> {

  private final OSMProvider provider;
  private final AddressKafkaExporter addressKafkaExporter;
  private final StreetKafkaExporter streetKafkaExporter;
  private final ExecutorService executorService = Executors.newFixedThreadPool(2);

  public MainExporter(
      @Value("${infrastructure.osm.filePath}") String filePath,
      AddressKafkaExporter addressKafkaExporter,
      StreetKafkaExporter streetKafkaExporter) {
    provider = new OSMProviderBzipFile(filePath);
    this.addressKafkaExporter = addressKafkaExporter;
    this.streetKafkaExporter = streetKafkaExporter;
  }

  @Override
  public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
    executorService.execute(() -> new AddressExporter(provider, addressKafkaExporter).export());
    executorService.execute(() -> new StreetExporter(provider, streetKafkaExporter).export());
  }
}
