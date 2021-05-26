package com.navigation.geocoding.ui.dto.response;

public class LocationResponseDto {
  private double latitude;
  private double longitude;

  public LocationResponseDto() {}

  public LocationResponseDto(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
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
}
