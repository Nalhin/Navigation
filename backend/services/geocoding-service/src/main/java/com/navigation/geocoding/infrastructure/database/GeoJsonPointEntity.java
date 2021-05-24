package com.navigation.geocoding.infrastructure.database;

import java.io.Serializable;
import java.util.List;

public class GeoJsonPointEntity implements Serializable {

  private List<Double> coordinates;
  private String type;

  public GeoJsonPointEntity() {
  }

  public GeoJsonPointEntity(List<Double> coordinates, String type) {
    this.coordinates = coordinates;
    this.type = type;
  }

  public List<Double> getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(List<Double> coordinates) {
    this.coordinates = coordinates;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
