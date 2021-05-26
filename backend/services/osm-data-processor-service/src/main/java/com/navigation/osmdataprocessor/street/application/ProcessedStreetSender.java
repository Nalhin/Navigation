package com.navigation.osmdataprocessor.street.application;

import com.navigation.osmdataprocessor.street.domain.StreetConnection;
import com.navigation.osmdataprocessor.street.domain.StreetNode;

public interface ProcessedStreetSender {
  void sendProcessedStreetConnection(String id, StreetConnection streetConnection);

  void sendProcessedStreetNode(String id, StreetNode streetNode);
}
