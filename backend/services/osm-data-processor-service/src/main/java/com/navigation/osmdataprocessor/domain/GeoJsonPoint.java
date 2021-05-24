package com.navigation.osmdataprocessor.domain;

import java.util.List;

public class GeoJsonPoint {
  private final double latitude;
  private final double longitude;

  public GeoJsonPoint(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getType() {
    return "Point";
  }

  public List<Double> getCoordinates() {
    return List.of(longitude, latitude);
  }
}
