package com.navigation.osmdataprocessor.domain.address;

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

  public double getLongitude() {
    return longitude;
  }

  public double getLatitude() {
    return latitude;
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

  @Override
  public String toString() {
    return "Address{"
        + "id="
        + id
        + ", longitude="
        + longitude
        + ", latitude="
        + latitude
        + ", city='"
        + city
        + '\''
        + ", country='"
        + country
        + '\''
        + ", houseNumber='"
        + houseNumber
        + '\''
        + ", street='"
        + street
        + '\''
        + ", postCode='"
        + postCode
        + '\''
        + '}';
  }
}
