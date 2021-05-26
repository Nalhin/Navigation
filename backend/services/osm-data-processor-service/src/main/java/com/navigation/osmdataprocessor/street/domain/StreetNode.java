package com.navigation.osmdataprocessor.street.domain;

import com.navigation.osmdataprocessor.shared.domain.GeoJsonPoint;
import java.util.Objects;

public class StreetNode {
  private final double latitude;
  private final double longitude;
  private final long id;

  public StreetNode(long id, double latitude, double longitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public long getId() {
    return id;
  }

  public GeoJsonPoint getLocation() {
    return new GeoJsonPoint(latitude, longitude);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StreetNode that = (StreetNode) o;
    return Double.compare(that.latitude, latitude) == 0
        && Double.compare(that.longitude, longitude) == 0
        && id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitude, longitude, id);
  }
}
