package com.navigation.geocoding.infrastructure.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "address")
class AddressEntity implements Serializable {

  @Id private long id;

  private GeoJsonPointEntity location;

  @Field(type = FieldType.Text)
  private String city;

  @Field(type = FieldType.Text)
  private String country;

  @Field(type = FieldType.Text)
  private String houseNumber;

  @Field(type = FieldType.Text)
  private String street;

  private String postCode;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public GeoJsonPointEntity getLocation() {
    return location;
  }

  public void setLocation(GeoJsonPointEntity location) {
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
