package com.navigation.osmdataprocessor.infrastructure.exporter;

import com.navigation.osmdataprocessor.domain.address.AddressExporter;
import com.navigation.osmdataprocessor.domain.address.AddressDataProcessor;
import com.navigation.osmdataprocessor.domain.street.StreetExporter;
import com.navigation.osmdataprocessor.domain.street.StreetDataProcessor;
import com.navigation.parser.provider.OSMProvider;
import com.navigation.parser.provider.OSMProviderBzipFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class RootExporter implements ApplicationListener<ApplicationReadyEvent> {

  private final ApplicationContext context;
  private final OSMProvider provider;
  private final AddressExporter addressExporter;
  private final StreetExporter streetExporter;
  private final ExecutorService executorService = Executors.newFixedThreadPool(2);

  public RootExporter(
      ApplicationContext context,
      @Value("${com.navigation.reversegeocodingapi.infrastructure.osm.filePath}") String filePath,
      AddressExporter addressExporter,
      StreetExporter streetExporter) {
    this.context = context;
    provider = new OSMProviderBzipFile(filePath);
    this.addressExporter = addressExporter;
    this.streetExporter = streetExporter;
  }

  @Override
  public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
    var address =
        executorService.submit(
            () -> new AddressDataProcessor(provider, addressExporter).processAndExport());
    var street =
        executorService.submit(
            () -> new StreetDataProcessor(provider, streetExporter).processAndExport());

    try {
      address.get();
      street.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    } finally {
      SpringApplication.exit(context, () -> 0);
    }
  }
}
