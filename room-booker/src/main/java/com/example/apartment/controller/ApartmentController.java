package com.example.apartment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartment.dto.request.ApartmentUpdateRequest;
import com.example.apartment.dto.response.ApartmentResponse;
import com.example.apartment.service.ApartmentService;
import com.example.auth.dto.JwtPayload;

@RestController
@RequestMapping("/api/v1/apartments")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(apartmentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentResponse> update(
            @PathVariable Long id,
            @RequestBody ApartmentUpdateRequest apartment,
            @AuthenticationPrincipal JwtPayload userPayload) {
        return ResponseEntity.ok(apartmentService.update(id, apartment, userPayload.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal JwtPayload userPayload) {
        apartmentService.delete(id, userPayload.getId());
        return ResponseEntity.noContent().build();
    }
}
