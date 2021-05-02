package com.navigation.parser.loader;

import com.navigation.parser.elements.ElementTypes;
import java.util.Map;

public class ExportSummary {

  private final Map<ElementTypes, Long> parsedCounter;
  private final Map<ElementTypes, Long> acceptedCounter;
  private final Map<ElementTypes, Long> exportedCounter;

  ExportSummary(
      Map<ElementTypes, Long> parsedCounter,
      Map<ElementTypes, Long> acceptedCounter,
      Map<ElementTypes, Long> exportedCounter) {
    this.parsedCounter = parsedCounter;
    this.acceptedCounter = acceptedCounter;
    this.exportedCounter = exportedCounter;
  }

  public long totalParsed() {
    return parsedCounter.values().stream().mapToLong(l -> l).sum();
  }

  public long totalAccepted() {
    return acceptedCounter.values().stream().mapToLong(l -> l).sum();
  }

  public long totalExported() {
    return exportedCounter.values().stream().mapToLong(l -> l).sum();
  }
}
