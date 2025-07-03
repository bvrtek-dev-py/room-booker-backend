package com.example.address.dto;

public final class AddressResponse {
    private final Long id;
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String country;
    private final Long objectId;

    public AddressResponse(Long id, String street, String city, String state, String zipCode, String country, Long objectId) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.objectId = objectId;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public Long getObjectId() {
        return objectId;
    }
}
