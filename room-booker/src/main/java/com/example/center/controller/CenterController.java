package com.example.center.controller;

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

import com.example.center.dto.request.CenterCreateRequest;
import com.example.center.dto.request.CenterUpdateRequest;
import com.example.center.dto.response.CenterResponse;
import com.example.center.service.CenterService;

@RestController
@RequestMapping("/api/v1/centers")
public class CenterController {

    @Autowired
    private CenterService centerService;

    @GetMapping
    public ResponseEntity<List<CenterResponse>> getAll() {
        List<CenterResponse> response = centerService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CenterResponse> getById(@PathVariable Long id) {
        CenterResponse response = centerService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CenterResponse> create(@RequestBody CenterCreateRequest center) {
        CenterResponse response = centerService.create(center);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CenterResponse> update(@PathVariable Long id, @RequestBody CenterUpdateRequest request) {
        CenterResponse response = centerService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        centerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}