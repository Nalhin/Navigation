package com.navigation.parser.elements;

import java.util.Map;

public final class Node extends TaggedElement implements Element {

  private final double latitude;
  private final double longitude;

  public Node(long id, Map<String, String> tags, double latitude, double longitude) {
    super(id, tags);
    this.latitude = latitude;
    this.longitude = longitude;
  }

  @Override
  public boolean accept(ElementVisitor visitor) {
    return visitor.accept(this);
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
}
