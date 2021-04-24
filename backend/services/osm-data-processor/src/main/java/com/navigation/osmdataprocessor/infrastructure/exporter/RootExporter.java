package com.navigation.osmdataprocessor.infrastructure.exporter;

import com.navigation.osmdataprocessor.domain.address.AddressExporter;
import com.navigation.osmdataprocessor.domain.address.AddressDataProcessor;
import com.navigation.osmdataprocessor.domain.street.StreetExporter;
import com.navigation.osmdataprocessor.domain.street.StreetDataProcessor;
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
public class RootExporter implements ApplicationListener<ApplicationReadyEvent> {

  private final OSMProvider provider;
  private final AddressExporter addressExporter;
  private final StreetExporter streetExporter;
  private final ExecutorService executorService = Executors.newFixedThreadPool(2);

  public RootExporter(
      @Value("${infrastructure.osm.filePath}") String filePath,
      AddressExporter addressExporter,
      StreetExporter streetExporter) {
    provider = new OSMProviderBzipFile(filePath);
    this.addressExporter = addressExporter;
    this.streetExporter = streetExporter;
  }

  @Override
  public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
    executorService.execute(() -> new AddressDataProcessor(provider, addressExporter).export());
    executorService.execute(() -> new StreetDataProcessor(provider, streetExporter).export());
  }
}
