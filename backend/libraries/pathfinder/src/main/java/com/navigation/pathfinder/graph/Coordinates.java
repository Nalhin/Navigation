package com.navigation.pathfinder.graph;

import java.util.Objects;

public class Coordinates {
  private final double latitude;
  private final double longitude;

  public Coordinates(double latitude, double longitude) {
    if (latitude < -90 || latitude > 90) {
      throw new IllegalArgumentException("Latitude must be between -90 and 90 inclusive");
    }
    if (longitude < -180 || longitude > 180) {
      throw new IllegalArgumentException("Longitude must be between -180 and 180 inclusive");
    }
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coordinates that = (Coordinates) o;
    return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitude, longitude);
  }
}
