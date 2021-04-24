package com.navigation.osmdataexporter.infrastructure.kafka.street;

public class StreetNodeDto {
  private double latitude;
  private double longitude;
  private long id;

  public StreetNodeDto(double latitude, double longitude, long id) {
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
}
