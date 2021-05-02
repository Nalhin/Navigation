package com.navigation.parser.loader;

import com.navigation.parser.elements.ElementTypes;
import java.util.EnumMap;
import java.util.Map;

class ExportSummarizer {

  private final Map<ElementTypes, Long> parsedCounter = new EnumMap<>(ElementTypes.class);
  private final Map<ElementTypes, Long> acceptedCounter = new EnumMap<>(ElementTypes.class);
  private final Map<ElementTypes, Long> exportedCounter = new EnumMap<>(ElementTypes.class);

  public void incrementParsed(ElementTypes elementTypes) {
    parsedCounter.put(elementTypes, parsedCounter.getOrDefault(elementTypes, 0L) + 1);
  }

  public void incrementAccepted(ElementTypes elementTypes) {
    acceptedCounter.put(elementTypes, acceptedCounter.getOrDefault(elementTypes, 0L) + 1);
  }

  public void incrementExported(ElementTypes elementTypes) {
    exportedCounter.put(elementTypes, exportedCounter.getOrDefault(elementTypes, 0L) + 1);
  }

  public ExportSummary toSummary(){
    return new ExportSummary(parsedCounter, acceptedCounter, exportedCounter);
  }
}
