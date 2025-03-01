package com.navigation.osmdataprocessor.address.domain;

import com.navigation.osmdataprocessor.shared.domain.GeoJsonPoint;
import java.util.Objects;

public class Address {

  private final long id;
  private final double longitude;
  private final double latitude;
  private final String city;
  private final String country;
  private final String houseNumber;
  private final String street;
  private final String postCode;

  public Address(
      long id,
      double longitude,
      double latitude,
      String city,
      String country,
      String houseNumber,
      String street,
      String postCode) {
    this.id = id;
    this.longitude = longitude;
    this.latitude = latitude;
    this.city = city;
    this.country = country;
    this.houseNumber = houseNumber;
    this.street = street;
    this.postCode = postCode;
  }

  public long getId() {
    return id;
  }

  public GeoJsonPoint getLocation() {
    return new GeoJsonPoint(latitude, longitude);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return id == address.id
        && Double.compare(address.longitude, longitude) == 0
        && Double.compare(address.latitude, latitude) == 0
        && Objects.equals(city, address.city)
        && Objects.equals(country, address.country)
        && Objects.equals(houseNumber, address.houseNumber)
        && Objects.equals(street, address.street)
        && Objects.equals(postCode, address.postCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, longitude, latitude, city, country, houseNumber, street, postCode);
  }

  public static class AddressBuilder {
    private long id;
    private double longitude;
    private double latitude;
    private String city;
    private String country;
    private String houseNumber;
    private String street;
    private String postCode;

    public AddressBuilder setId(long id) {
      this.id = id;
      return this;
    }

    public AddressBuilder setLongitude(double longitude) {
      this.longitude = longitude;
      return this;
    }

    public AddressBuilder setLatitude(double latitude) {
      this.latitude = latitude;
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
      return new Address(id, longitude, latitude, city, country, houseNumber, street, postCode);
    }
  }
}
