package com.example.reservation.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.example.apartment.entity.ApartmentEntity;
import com.example.user.entity.UserEntity;

@Entity
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private ApartmentEntity apartment;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private int numberOfGuests;

    @Column(nullable = false)
    private double totalPrice;

    protected ReservationEntity() {}

    public ReservationEntity(
            Long id,
            UserEntity user,
            ApartmentEntity apartment,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int numberOfGuests,
            double totalPrice
    ) {
        this.id = id;
        this.user = user;
        this.apartment = apartment;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public ApartmentEntity getApartment() {
        return apartment;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
