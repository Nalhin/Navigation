package com.navigation.geocodingapi.api;

import com.navigation.geocodingapi.infrastructure.AddressEntity;

public class AddressDto {
  private long id;
  private LocationDto location;
  private String city;
  private String country;
  private String houseNumber;
  private String street;
  private String postCode;

  public static AddressDto fromEntity(AddressEntity entity) {
    var dto = new AddressDto();
    dto.setCity(entity.getCity());
    dto.setCountry(entity.getCountry());
    dto.setId(entity.getId());
    dto.setLocation(new LocationDto(entity.getLocation().getY(), entity.getLocation().getX()));
    dto.setHouseNumber(entity.getHouseNumber());
    dto.setStreet(entity.getStreet());
    dto.setPostCode(entity.getPostCode());
    return dto;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocationDto getLocation() {
    return location;
  }

  public void setLocation(LocationDto location) {
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
