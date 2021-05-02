package com.navigation.parser.elements;

import java.util.Objects;

public final class Bounds implements Element {

  private final double minLatitude;
  private final double maxLatitude;
  private final double minLongitude;
  private final double maxLongitude;

  public Bounds(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
    this.minLatitude = minLatitude;
    this.maxLatitude = maxLatitude;
    this.minLongitude = minLongitude;
    this.maxLongitude = maxLongitude;
  }

  @Override
  public boolean accept(ElementVisitor visitor) {
    return visitor.accept(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Bounds bounds = (Bounds) o;
    return Objects.equals(minLatitude, bounds.minLatitude)
        && Objects.equals(maxLatitude, bounds.maxLatitude)
        && Objects.equals(minLongitude, bounds.minLongitude)
        && Objects.equals(maxLongitude, bounds.maxLongitude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minLatitude, maxLatitude, minLongitude, maxLongitude);
  }

  public double getMinLatitude() {
    return minLatitude;
  }

  public double getMaxLatitude() {
    return maxLatitude;
  }

  public double getMinLongitude() {
    return minLongitude;
  }

  public double getMaxLongitude() {
    return maxLongitude;
  }

  @Override
  public String toString() {
    return "Bounds{"
        + "minLatitude='"
        + minLatitude
        + '\''
        + ", maxLatitude='"
        + maxLatitude
        + '\''
        + ", minLongitude='"
        + minLongitude
        + '\''
        + ", maxLongitude='"
        + maxLongitude
        + '\''
        + '}';
  }
}
