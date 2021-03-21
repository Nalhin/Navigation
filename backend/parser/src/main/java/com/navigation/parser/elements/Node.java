package com.navigation.parser.elements;

import java.util.Map;
import java.util.Objects;

public class Node extends TaggedElement {
  private final double latitude;
  private final double longitude;

  public Node(long id, Map<String, String> tags, double latitude, double longitude) {
    super(id, tags);
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

}
