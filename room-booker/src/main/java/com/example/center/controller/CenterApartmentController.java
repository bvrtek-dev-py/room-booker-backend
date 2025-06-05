package com.example.center.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartment.dto.request.ApartmentCreateRequest;
import com.example.apartment.dto.response.ApartmentResponse;
import com.example.apartment.service.ApartmentService;
import com.example.auth.dto.JwtPayload;

@RestController
@RequestMapping("/api/v1/cenetrs/{id}/apartments")
public class CenterApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @PostMapping
    public ResponseEntity<ApartmentResponse> create(
            @RequestBody ApartmentCreateRequest apartment,
            @PathVariable("id") Long centerId,
            @AuthenticationPrincipal JwtPayload jwtPayload) {
        return ResponseEntity.ok(apartmentService.create(apartment, centerId, jwtPayload));
    }

    @GetMapping
    public ResponseEntity<List<ApartmentResponse>> getByCenterId(
            @PathVariable("id") Long centerId, @AuthenticationPrincipal JwtPayload jwtPayload) {
        return ResponseEntity.ok(apartmentService.getByCenterId(centerId, jwtPayload.getId()));
    }
}
