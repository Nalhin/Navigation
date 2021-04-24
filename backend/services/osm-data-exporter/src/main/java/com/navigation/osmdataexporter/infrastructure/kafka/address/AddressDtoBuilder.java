package com.navigation.osmdataexporter.infrastructure.kafka.address;

public class AddressDtoBuilder {
    private long id;
    private double longitude;
    private double latitude;
    private String city;
    private String country;
    private String houseNumber;
    private String street;
    private String postCode;

    public AddressDtoBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public AddressDtoBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public AddressDtoBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public AddressDtoBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public AddressDtoBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public AddressDtoBuilder setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public AddressDtoBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressDtoBuilder setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public AddressDto createAddressDto() {
        return new AddressDto(id, longitude, latitude, city, country, houseNumber, street, postCode);
    }
}