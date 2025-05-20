package com.example.apartment.entity;

import java.util.List;

import com.example.apartment.type.Facility;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ApartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 255)
    private String name;
    @Column(nullable = false)
    private int numberOfPeople;
    @Column(nullable = false, length = 500)
    private String description;
    @Column(nullable = false)
    private double pricePerNight;
    @Column(nullable = false)
    private int amount;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Facility> facilities;

    protected ApartmentEntity() {}

    public ApartmentEntity(Long id, String name, int numberOfPeople, String description, double pricePerNight, int amount, List<Facility> facilities) {
        this.id = id;
        this.name = name;
        this.numberOfPeople = numberOfPeople;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.amount = amount;
        this.facilities = facilities;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getDescription() {
        return description;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getAmount() {
        return amount;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }
}