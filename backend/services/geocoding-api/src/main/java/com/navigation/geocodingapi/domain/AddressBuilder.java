package com.navigation.geocodingapi.domain;

public class AddressBuilder {
  private long id;
  private Location location;
  private String city;
  private String country;
  private String houseNumber;
  private String street;
  private String postCode;

  public AddressBuilder setId(long id) {
    this.id = id;
    return this;
  }

  public AddressBuilder setLocation(Location location) {
    this.location = location;
    return this;
  }

  public AddressBuilder setCity(String city) {
    this.city = city;
    return this;
  }

  public AddressBuilder setCountry(String country) {
    this.country = country;
    return this;
  }

  public AddressBuilder setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
    return this;
  }

  public AddressBuilder setStreet(String street) {
    this.street = street;
    return this;
  }

  public AddressBuilder setPostCode(String postCode) {
    this.postCode = postCode;
    return this;
  }

  public Address createAddress() {
    return new Address(id, location, city, country, houseNumber, street, postCode);
  }
}
