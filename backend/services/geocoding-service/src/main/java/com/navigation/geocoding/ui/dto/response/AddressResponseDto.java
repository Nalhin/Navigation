package com.navigation.geocoding.ui.dto.response;

public class AddressResponseDto {
  private long id;
  private LocationResponseDto location;
  private String city;
  private String country;
  private String houseNumber;
  private String street;
  private String postCode;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocationResponseDto getLocation() {
    return location;
  }

  public void setLocation(LocationResponseDto location) {
    this.location = location;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }
}
