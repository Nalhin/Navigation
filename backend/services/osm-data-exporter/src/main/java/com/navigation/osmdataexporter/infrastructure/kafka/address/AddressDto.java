package com.navigation.osmdataexporter.infrastructure.kafka.address;

public class AddressDto {

    private long id;
    private double longitude;
    private double latitude;
    private String city;
    private String country;
    private String houseNumber;
    private String street;
    private String postCode;

    public AddressDto(long id, double longitude, double latitude, String city, String country, String houseNumber, String street, String postCode) {
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

    public String getPostCode() {
        return postCode;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", street='" + street + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
