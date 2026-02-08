package com.example.company.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.example.auth.dto.JwtPayload;
import com.example.company.dto.request.CompanyCreateRequest;
import com.example.company.dto.request.CompanyUpdateRequest;
import com.example.company.dto.response.CompanyResponse;
import com.example.company.service.CompanyService;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAll() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> create(
        @RequestBody CompanyCreateRequest request, @AuthenticationPrincipal JwtPayload user
    ) {
        CompanyResponse response = companyService.create(request, user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> update(
        @PathVariable Long id,
        @RequestBody CompanyUpdateRequest request,
        @AuthenticationPrincipal JwtPayload user
    ) {
        return ResponseEntity.ok(companyService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id, @AuthenticationPrincipal JwtPayload user) {
        companyService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
