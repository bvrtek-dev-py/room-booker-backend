package com.example.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.apartment.entity.ApartmentEntity;
import com.example.reservation.entity.ReservationEntity;
import com.example.user.entity.UserEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long>,
        JpaSpecificationExecutor<ReservationEntity> {
    List<ReservationEntity> findByUser(UserEntity user);

    List<ReservationEntity> findByApartment(ApartmentEntity apartment);

    long countInDateRange(Specification<ReservationEntity> spec);
}
