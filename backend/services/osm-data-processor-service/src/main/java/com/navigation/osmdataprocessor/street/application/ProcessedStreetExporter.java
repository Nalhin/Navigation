package com.navigation.osmdataprocessor.street.application;

import com.navigation.osmdataprocessor.street.domain.StreetConnection;
import com.navigation.osmdataprocessor.street.domain.StreetNode;

public interface ProcessedStreetExporter {
  void exportProcessedStreetConnection(String id, StreetConnection streetConnection);

  void exportProcessedStreetNode(String id, StreetNode streetNode);
}
