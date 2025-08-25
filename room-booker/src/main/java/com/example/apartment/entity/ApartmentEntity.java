package com.example.apartment.entity;

import java.util.List;

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
            CenterEntity center
    ) {
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
        Long id,
        String name,
        Integer numberOfPeople,
        String description,
        Double pricePerNight,
        Integer amount,
        List<Facility> facilities,
        CenterEntity center
    ) {
        ApartmentEntity newApartment = new ApartmentEntity();

        newApartment.id = (id != null) ? id : this.id;
        newApartment.name = (name != null) ? name : this.name;
        newApartment.numberOfPeople = (numberOfPeople != null) ? numberOfPeople : this.numberOfPeople;
        newApartment.description = (description != null) ? description : this.description;
        newApartment.pricePerNight = (pricePerNight != null) ? pricePerNight : this.pricePerNight;
        newApartment.amount = (amount != null) ? amount : this.amount;
        newApartment.facilities = (facilities != null) ? facilities : this.facilities;
        newApartment.center = (center != null) ? center : this.center;

        return newApartment;
    }
}
