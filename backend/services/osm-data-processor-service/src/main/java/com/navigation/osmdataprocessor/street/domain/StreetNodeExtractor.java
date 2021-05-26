package com.navigation.osmdataprocessor.street.domain;

import com.navigation.parser.elements.Node;
import org.springframework.stereotype.Component;

@Component
public class StreetNodeExtractor {

  public StreetNode extractFromNode(Node node) {
    return new StreetNode(node.getId(), node.getLatitude(), node.getLongitude());
  }
}
