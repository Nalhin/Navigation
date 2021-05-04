package com.navigation.osmdataprocessor.infrastructure.exporter;

import com.navigation.osmdataprocessor.domain.DataProcessor;
import com.navigation.osmdataprocessor.domain.address.AddressExporter;
import com.navigation.osmdataprocessor.domain.address.AddressDataProcessor;
import com.navigation.osmdataprocessor.domain.address.ProcessedAddressExporter;
import com.navigation.osmdataprocessor.domain.street.ProcessedStreetExporter;
import com.navigation.osmdataprocessor.domain.street.StreetExporter;
import com.navigation.osmdataprocessor.domain.street.StreetDataProcessor;
import com.navigation.parser.loader.ExportSummary;
import com.navigation.parser.provider.OSMProvider;
import com.navigation.parser.provider.OSMProviderBzipFile;
import com.navigation.parser.provider.OSMProviderFile;
import io.vavr.concurrent.Future;
import java.text.MessageFormat;
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
  private final ProcessedAddressExporter processedAddressExporter;
  private final ProcessedStreetExporter processedStreetExporter;
  private final String filePath;

  public RootExporter(
      ApplicationContext context,
      @Value("${infrastructure.osm.filePath}") String filePath,
      ProcessedAddressExporter processedAddressExporter,
      ProcessedStreetExporter processedStreetExporter) {
    this.context = context;
    this.provider = new OSMProviderBzipFile(filePath);
    this.filePath = filePath;
    this.processedAddressExporter = processedAddressExporter;
    this.processedStreetExporter = processedStreetExporter;
  }

  private List<DataProcessor> prepareProcessors() {
    return List.of(
        new AddressDataProcessor(provider, new AddressExporter(processedAddressExporter)),
        new StreetDataProcessor(provider, new StreetExporter(processedStreetExporter)));
  }

  @Override
  public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
    logger.info("Exporting file: " + filePath);
    var startTime = Instant.now();

    var futures =
        prepareProcessors().stream()
            .map(dataProcessor -> Future.of(dataProcessor::processAndExport))
            .map(
                future ->
                    future.onComplete(
                        (res) ->
                            res.onSuccess((val) -> logger.info(printSummary(startTime, val.get())))
                                .onFailure((e) -> logger.error(e.getMessage()))))
            .collect(Collectors.toList());

    futures.forEach(Future::await);

    logger.info("Export completed");
    logger.info("Shutting down!!!");
    SpringApplication.exit(context, () -> 0);
  }

  private String printSummary(Instant startTime, ExportSummary summary) {
    var sb = new StringBuilder();
    sb.append("\n");
    sb.append("\n");
    sb.append("Export summary\n");
    sb.append(summary.toString()).append("\n");
    sb.append(
        MessageFormat.format(
            "Started at {0} \n", startTime.atZone(ZoneOffset.systemDefault()).toLocalTime()));
    sb.append(MessageFormat.format("Finished at {0}", LocalTime.now())).append("\n");
    sb.append(
            MessageFormat.format(
                "Export took {0} seconds",
                ChronoUnit.MILLIS.between(startTime, Instant.now()) / 1000.0))
        .append("\n");
    return sb.toString();
  }
}
