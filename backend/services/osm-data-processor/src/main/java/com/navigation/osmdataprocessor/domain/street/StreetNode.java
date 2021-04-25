package com.navigation.osmdataprocessor.domain.street;

import com.navigation.osmdataprocessor.domain.GeoJsonPoint;

public class StreetNode {
  private final double latitude;
  private final double longitude;
  private final long id;

  public StreetNode(double latitude, double longitude, long id) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public GeoJsonPoint getLocation() {
    return new GeoJsonPoint(latitude, longitude);
  }
}
