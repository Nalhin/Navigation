package com.navigation.osmdataprocessor.domain.street;

public class StreetNode {
  private final double latitude;
  private final double longitude;
  private final long id;

  public StreetNode(double latitude, double longitude, long id) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.id = id;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public long getId() {
    return id;
  }
}
