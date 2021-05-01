package com.navigation.geocodingapi.domain;

public class Address {
  private final long id;
  private final Location location;
  private final String city;
  private final String country;
  private final String houseNumber;
  private final String street;
  private final String postCode;

  public Address(
      long id,
      Location location,
      String city,
      String country,
      String houseNumber,
      String street,
      String postCode) {
    this.id = id;
    this.location = location;
    this.city = city;
    this.country = country;
    this.houseNumber = houseNumber;
    this.street = street;
    this.postCode = postCode;
  }

  public long getId() {
    return id;
  }

  public Location getLocation() {
    return location;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public String getStreet() {
    return street;
  }

  public String getPostCode() {
    return postCode;
  }
}
