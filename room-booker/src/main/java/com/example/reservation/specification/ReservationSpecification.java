package com.example.reservation.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.apartment.entity.ApartmentEntity;
import com.example.reservation.entity.ReservationEntity;

public class ReservationSpecification {

    /**
     * Specification do wyszukiwania rezerwacji, które zachodzą na siebie w danym okresie.
     * Używane do sprawdzania dostępności apartamentu.
     *
     * @param apartment Apartament do sprawdzenia
     * @param checkInDate Data zaciągnięcia
     * @param checkOutDate Data opuszczenia
     * @return Specification dla JPA Query
     */
    public static Specification<ReservationEntity> overlapsWithDateRange(
            ApartmentEntity apartment, LocalDate checkInDate, LocalDate checkOutDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
            criteriaBuilder.equal(root.get("apartment"), apartment),
            criteriaBuilder.lessThan(root.get("checkInDate"), checkOutDate),
            criteriaBuilder.greaterThan(root.get("checkOutDate"), checkInDate)
        );
    }
}
