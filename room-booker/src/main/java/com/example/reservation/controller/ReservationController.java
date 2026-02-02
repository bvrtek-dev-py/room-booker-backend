package com.example.reservation.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.dto.JwtPayload;
import com.example.reservation.dto.request.ReservationCreateRequest;
import com.example.reservation.dto.response.AvailableApartmentResponse;
import com.example.reservation.dto.response.ReservationResponse;
import com.example.reservation.service.ReservationService;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @RequestBody ReservationCreateRequest request, @AuthenticationPrincipal JwtPayload userPayload) {
        ReservationResponse response = reservationService.create(request, userPayload.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/my-reservations")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(
            @AuthenticationPrincipal JwtPayload userPayload) {
        List<ReservationResponse> reservations = reservationService.getByUser(userPayload.getId());
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/apartment/{apartmentId}")
    public ResponseEntity<List<ReservationResponse>> getApartmentReservations(@PathVariable Long apartmentId) {
        List<ReservationResponse> reservations = reservationService.getByApartment(apartmentId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/available")
    public ResponseEntity<List<AvailableApartmentResponse>> getAvailableApartments(
            @RequestParam Long centerId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate) {
        List<AvailableApartmentResponse> available =
                reservationService.getAvailableApartments(centerId, checkInDate, checkOutDate);
        return ResponseEntity.ok(available);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable Long id, @AuthenticationPrincipal JwtPayload userPayload) {
        reservationService.cancel(id, userPayload.getId());
        return ResponseEntity.noContent().build();
    }
}
