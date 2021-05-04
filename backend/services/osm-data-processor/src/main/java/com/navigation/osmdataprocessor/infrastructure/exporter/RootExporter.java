package com.navigation.osmdataprocessor.infrastructure.exporter;

import com.navigation.osmdataprocessor.domain.DataProcessor;
import com.navigation.osmdataprocessor.domain.address.AddressExporter;
import com.navigation.osmdataprocessor.domain.address.AddressDataProcessor;
import com.navigation.osmdataprocessor.domain.street.StreetExporter;
import com.navigation.osmdataprocessor.domain.street.StreetDataProcessor;
import com.navigation.parser.loader.ExportSummary;
import com.navigation.parser.provider.OSMProvider;
import com.navigation.parser.provider.OSMProviderFile;
import io.vavr.concurrent.Future;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RootExporter implements ApplicationListener<ApplicationReadyEvent> {

  private static final Logger logger = LoggerFactory.getLogger(RootExporter.class);
  private final ApplicationContext context;
  private final OSMProvider provider;
  private final AddressExporter addressExporter;
  private final StreetExporter streetExporter;
  private final String filePath;

  public RootExporter(
      ApplicationContext context,
      @Value("${infrastructure.osm.filePath}") String filePath,
      AddressExporter addressExporter,
      StreetExporter streetExporter) {
    this.context = context;
    this.provider = new OSMProviderFile(filePath);
    this.filePath = filePath;
    this.addressExporter = addressExporter;
    this.streetExporter = streetExporter;
  }

  private List<DataProcessor> prepareProcessors() {
    return List.of(
        new AddressDataProcessor(provider, addressExporter),
        new StreetDataProcessor(provider, streetExporter));
  }

  @Override
  public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
    logger.info("Beginning export of file " + filePath);
    var startTime = Instant.now();

    var futures =
        prepareProcessors().stream()
            .map(dataProcessor -> Future.of(dataProcessor::processAndExport))
            .map(
                future ->
                    future.onComplete(
                        (res) ->
                            res.onSuccess((val) -> printSummary(startTime, val.get()))
                                .onFailure((e) -> logger.error(e.getMessage()))))
            .collect(Collectors.toList());
    futures.forEach(Future::await);

    logger.info("Export finished");
    logger.info("Shutting down");
    SpringApplication.exit(context, () -> 0);
  }

  private void printSummary(Instant startTime, ExportSummary summary) {
    logger.info("Export summary");
    logger.info(summary.toString());
    logger.info(
        MessageFormat.format(
            "Started at {0}", startTime.atZone(ZoneOffset.systemDefault()).toLocalTime()));
    logger.info(MessageFormat.format("Finished at {0}", LocalTime.now()));
    logger.info(
        MessageFormat.format(
            "Export took {0} seconds",
            ChronoUnit.MILLIS.between(startTime, Instant.now()) / 1000.0));
  }
}
