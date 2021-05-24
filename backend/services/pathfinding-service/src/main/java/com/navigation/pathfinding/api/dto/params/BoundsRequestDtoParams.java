package com.navigation.pathfinding.api.dto.params;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class BoundsRequestDtoParams {

  @NotNull private Double minLatitude;
  @NotNull private Double minLongitude;
  @NotNull private Double maxLatitude;
  @NotNull private Double maxLongitude;

  public Double getMinLatitude() {
    return minLatitude;
  }

  public void setMinLatitude(Double minLatitude) {
    this.minLatitude = minLatitude;
  }

  public Double getMinLongitude() {
    return minLongitude;
  }

  public void setMinLongitude(Double minLongitude) {
    this.minLongitude = minLongitude;
  }

  public Double getMaxLatitude() {
    return maxLatitude;
  }

  public void setMaxLatitude(Double maxLatitude) {
    this.maxLatitude = maxLatitude;
  }

  public Double getMaxLongitude() {
    return maxLongitude;
  }

  public void setMaxLongitude(Double maxLongitude) {
    this.maxLongitude = maxLongitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BoundsRequestDtoParams that = (BoundsRequestDtoParams) o;
    return Objects.equals(minLatitude, that.minLatitude)
        && Objects.equals(minLongitude, that.minLongitude)
        && Objects.equals(maxLatitude, that.maxLatitude)
        && Objects.equals(maxLongitude, that.maxLongitude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minLatitude, minLongitude, maxLatitude, maxLongitude);
  }
}
