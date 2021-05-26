package com.navigation.osmdataprocessor.street.domain;

import com.navigation.parser.elements.Node;

public class StreetNodeExtractor {

  public StreetNode extractFromNode(Node node) {
    return new StreetNode(node.getId(), node.getLatitude(), node.getLongitude());
  }
}
