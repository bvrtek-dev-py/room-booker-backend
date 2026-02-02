package com.example.reservation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apartment.entity.ApartmentEntity;
import com.example.apartment.repository.ApartmentRepository;
import com.example.center.entity.CenterEntity;
import com.example.center.repository.CenterRepository;
import com.example.common.exception.ObjectNotFoundException;
import com.example.common.exception.PermissionDeniedException;
import com.example.reservation.dto.request.ReservationCreateRequest;
import com.example.reservation.dto.response.AvailableApartmentResponse;
import com.example.reservation.dto.response.ReservationResponse;
import com.example.reservation.entity.ReservationEntity;
import com.example.reservation.exception.ApartmentNotAvailableException;
import com.example.reservation.exception.GuestLimitExceededException;
import com.example.reservation.exception.ReservationNotFoundException;
import com.example.reservation.mapper.AvailableApartmentResponseMapper;
import com.example.reservation.mapper.ReservationEntityMapper;
import com.example.reservation.mapper.ReservationResponseMapper;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.specification.ReservationSpecification;
import com.example.user.entity.UserEntity;
import com.example.user.repository.UserRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationEntityMapper reservationEntityMapper;

    @Autowired
    private ReservationResponseMapper reservationResponseMapper;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private AvailableApartmentResponseMapper availableApartmentResponseMapper;

    public ReservationResponse create(ReservationCreateRequest request, Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException());

        ApartmentEntity apartment =
                apartmentRepository.findById(request.getApartmentId()).orElseThrow(() -> new ObjectNotFoundException());

        long reservedRooms = reservationRepository.countInDateRange(
                ReservationSpecification.overlapsWithDateRange(
                        apartment, request.getCheckInDate(), request.getCheckOutDate()));

        if (reservedRooms >= apartment.getAmount()) {
            throw new ApartmentNotAvailableException("Apartment is not available for the selected dates");
        }

        if (request.getNumberOfGuests() > apartment.getNumberOfPeople()) {
            throw new GuestLimitExceededException("Number of guests exceeds apartment capacity");
        }

        ReservationEntity reservation = reservationEntityMapper.map(request, user, apartment);
        ReservationEntity savedReservation = reservationRepository.save(reservation);

        return reservationResponseMapper.map(savedReservation);
    }

    public List<ReservationResponse> getByUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException());

        return reservationRepository.findByUser(user).stream()
                .map(reservationResponseMapper::map)
                .collect(Collectors.toList());
    }

    public List<ReservationResponse> getByApartment(Long apartmentId) {
        ApartmentEntity apartment =
                apartmentRepository.findById(apartmentId).orElseThrow(() -> new ObjectNotFoundException());

        return reservationRepository.findByApartment(apartment).stream()
                .map(reservationResponseMapper::map)
                .collect(Collectors.toList());
    }

    public List<AvailableApartmentResponse> getAvailableApartments(
            Long centerId, LocalDate checkInDate, LocalDate checkOutDate) {
        CenterEntity center = centerRepository.findById(centerId).orElseThrow(() -> new ObjectNotFoundException());

        List<ApartmentEntity> apartments = apartmentRepository.findByCenter(center);

        return apartments.stream()
                .filter(apartment -> {
                    long reservedRooms = reservationRepository.count(
                            ReservationSpecification.overlapsWithDateRange(apartment, checkInDate, checkOutDate));
                    return reservedRooms < apartment.getAmount();
                })
                .map(apartment -> {
                    long reservedRooms = reservationRepository.count(
                            ReservationSpecification.overlapsWithDateRange(apartment, checkInDate, checkOutDate));
                    int availableRooms = (int) (apartment.getAmount() - reservedRooms);
                    return availableApartmentResponseMapper.map(
                            apartment, checkInDate, checkOutDate, apartment.getAmount(), (int) reservedRooms, availableRooms);
                })
                .collect(Collectors.toList());
    }

    public void cancel(Long reservationId, Long userId) {
        ReservationEntity reservation =
                reservationRepository.findById(reservationId).orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        if (!reservation.getUser().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }

        reservationRepository.deleteById(reservationId);
    }
}
