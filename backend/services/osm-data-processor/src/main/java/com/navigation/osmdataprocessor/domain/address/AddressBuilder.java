package com.navigation.osmdataprocessor.domain.address;

class AddressBuilder {
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

    public Address createAddressDto() {
        return new Address(id, longitude, latitude, city, country, houseNumber, street, postCode);
    }
}