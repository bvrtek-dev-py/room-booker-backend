package com.example.reservation.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.apartment.entity.ApartmentEntity;
import com.example.reservation.entity.ReservationEntity;

public class ReservationSpecification {
    public static Specification<ReservationEntity> overlapsWithDateRange(
            ApartmentEntity apartment, LocalDate checkInDate, LocalDate checkOutDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
            criteriaBuilder.equal(root.get("apartment"), apartment),
            criteriaBuilder.lessThan(root.get("checkInDate"), checkOutDate),
            criteriaBuilder.greaterThan(root.get("checkOutDate"), checkInDate)
        );
    }
}
