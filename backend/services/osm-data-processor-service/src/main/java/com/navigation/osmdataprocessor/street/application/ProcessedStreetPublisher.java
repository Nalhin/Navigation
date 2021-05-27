package com.navigation.osmdataprocessor.street.application;

import com.navigation.osmdataprocessor.street.domain.StreetConnection;
import com.navigation.osmdataprocessor.street.domain.StreetNode;

public interface ProcessedStreetPublisher {
  void publishProcessedStreetConnection(String id, StreetConnection streetConnection);

  void publishProcessedStreetNode(String id, StreetNode streetNode);
}
