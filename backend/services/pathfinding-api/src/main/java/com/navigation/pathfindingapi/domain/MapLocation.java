package com.navigation.pathfindingapi.domain;

public class MapLocation {
  private final double longitude;
  private final double latitude;

  public MapLocation(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public double getLatitude() {
    return latitude;
  }
}
