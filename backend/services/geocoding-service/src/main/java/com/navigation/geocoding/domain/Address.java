package com.navigation.geocoding.domain;

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

  public static class AddressBuilder {
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
}
