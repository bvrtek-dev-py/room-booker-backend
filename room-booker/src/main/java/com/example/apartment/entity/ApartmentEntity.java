package com.example.apartment.entity;

import java.util.List;
import java.util.Optional;

import com.example.apartment.type.Facility;
import com.example.center.entity.CenterEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
    @CollectionTable(name = "apartment_facilities", joinColumns = @JoinColumn(name = "apartment_id"))
    @Column(name = "facility")
    @Enumerated(EnumType.STRING)
    private List<Facility> facilities;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    private CenterEntity center;

    protected ApartmentEntity() {}

    public ApartmentEntity(
            Long id,
            String name,
            int numberOfPeople,
            String description,
            double pricePerNight,
            int amount,
            List<Facility> facilities,
            CenterEntity center) {
        this.id = id;
        this.name = name;
        this.numberOfPeople = numberOfPeople;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.amount = amount;
        this.facilities = facilities;
        this.center = center;
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

    public CenterEntity getCenter() {
        return center;
    }

    public ApartmentEntity with(
            Optional<String> newName,
            Optional<Integer> newNumberOfPeople,
            Optional<String> newDescription,
            Optional<Double> newPricePerNight,
            Optional<Integer> newAmount,
            Optional<List<Facility>> newFacilities,
            Optional<CenterEntity> newCenter) {
        ApartmentEntity newApartment = new ApartmentEntity();

        newApartment.id = this.id;
        newApartment.name = newName.orElse(this.name);
        newApartment.numberOfPeople = newNumberOfPeople.orElse(this.numberOfPeople);
        newApartment.description = newDescription.orElse(this.description);
        newApartment.pricePerNight = newPricePerNight.orElse(this.pricePerNight);
        newApartment.amount = newAmount.orElse(this.amount);
        newApartment.facilities = newFacilities.orElse(this.facilities);
        newApartment.center = newCenter.orElse(this.center);

        return newApartment;
    }
}
