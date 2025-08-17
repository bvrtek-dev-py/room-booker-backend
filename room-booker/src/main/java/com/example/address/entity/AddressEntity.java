package com.example.address.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "reference_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "addresses")
@Access(AccessType.FIELD)
public abstract class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String street;

    @Column(nullable = false, length = 255)
    private String city;

    @Column(nullable = false, length = 255)
    private String state;

    @Column(nullable = false, length = 10)
    private String zipCode;

    @Column(nullable = false, length = 255)
    private String country;

    @Column(nullable = false)
    private Long objectId;

    public AddressEntity() {}

    public AddressEntity(Long id, String street, String city, String state, String zipCode, String country, Long objectId) {
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

    public abstract AddressEntity with(String street, String city, String state, String zipCode, String country);
}
