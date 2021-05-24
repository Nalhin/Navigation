package com.navigation.osmdataprocessor.domain.street;

public interface ProcessedStreetExporter {
  void exportProcessedStreetConnection(String id, StreetConnection streetConnection);

  void exportProcessedStreetNode(String id, StreetNode streetNode);
}
