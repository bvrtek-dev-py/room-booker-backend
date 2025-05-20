package com.example.apartment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartment.dto.request.ApartmentCreateRequest;
import com.example.apartment.dto.request.ApartmentUpdateRequest;
import com.example.apartment.dto.response.ApartmentResponse;
import com.example.apartment.service.ApartmentService;

@RestController
@RequestMapping("/api/v1/apartments")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public ResponseEntity<List<ApartmentResponse>> getAll() {
        return ResponseEntity.ok(apartmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(apartmentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ApartmentResponse> create(
        @RequestBody ApartmentCreateRequest apartment
    ) {
        return ResponseEntity.ok(apartmentService.create(apartment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentResponse> update(
        @PathVariable Long id, 
        @RequestBody ApartmentUpdateRequest apartment
    ) {
        return ResponseEntity.ok(apartmentService.update(id, apartment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        apartmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}