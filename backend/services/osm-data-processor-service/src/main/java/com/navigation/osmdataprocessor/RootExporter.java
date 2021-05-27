package com.navigation.osmdataprocessor;

import com.navigation.osmdataprocessor.address.application.AddressProcessor;
import com.navigation.osmdataprocessor.street.application.StreetProcessor;
import com.navigation.parser.loader.ExportSummary;
import io.vavr.concurrent.Future;
import java.text.MessageFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RootExporter implements ApplicationListener<ApplicationReadyEvent> {

  private static final Logger logger = LoggerFactory.getLogger(RootExporter.class);
  private final ApplicationContext context;
  private final Clock clock;
  private final AddressProcessor addressProcessor;
  private final StreetProcessor streetDataProcessor;

  public RootExporter(
      ApplicationContext context,
      Clock clock,
      AddressProcessor addressProcessor,
      StreetProcessor streetDataProcessor) {
    this.context = context;
    this.clock = clock;
    this.addressProcessor = addressProcessor;
    this.streetDataProcessor = streetDataProcessor;
  }

  @Override
  public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
    logger.info("Exporting file");
    var startTime = Instant.now(clock);

    List.of(addressProcessor, streetDataProcessor).stream()
        .map(OSMProcessor -> Future.of(OSMProcessor::processAndPublish))
        .map(
            future ->
                future.onComplete(
                    (futureResult) ->
                        futureResult
                            .onSuccess(
                                (result) -> logger.info(printSummary(startTime, result.get())))
                            .onFailure((error) -> logger.error(error.getMessage()))))
        .forEach(Future::await);

    logger.info("Export completed");

    if (Arrays.asList(context.getEnvironment().getActiveProfiles()).contains("prod")) {
      logger.info("Shutting down!!!");
      SpringApplication.exit(context, () -> 0);
    }
  }

  private String printSummary(Instant startTime, ExportSummary summary) {
    return "\n"
        + "\n"
        + "Export summary\n"
        + summary.toString()
        + "\n"
        + MessageFormat.format(
            "Started at {0} \n", startTime.atZone(ZoneOffset.systemDefault()).toLocalTime())
        + MessageFormat.format("Finished at {0}", LocalTime.now(clock))
        + "\n"
        + MessageFormat.format(
            "Export took {0} seconds", ChronoUnit.SECONDS.between(startTime, Instant.now(clock)))
        + "\n";
  }
}
