package com.navigation.osmdataprocessor.shared.domain;

import java.util.List;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GeoJsonPoint that = (GeoJsonPoint) o;
    return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitude, longitude);
  }
}
