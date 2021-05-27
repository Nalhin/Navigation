package com.navigation.pathfinding.ui.dto.response;

import java.util.Objects;

public class NodeResponseDto {
  private double latitude;
  private double longitude;
  private long id;

  public NodeResponseDto() {}

  public NodeResponseDto(double latitude, double longitude, long id) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.id = id;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NodeResponseDto that = (NodeResponseDto) o;
    return Double.compare(that.latitude, latitude) == 0
        && Double.compare(that.longitude, longitude) == 0
        && id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitude, longitude, id);
  }
}
