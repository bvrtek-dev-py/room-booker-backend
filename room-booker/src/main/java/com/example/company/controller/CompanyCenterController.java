package com.example.company.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.example.auth.dto.JwtPayload;
import com.example.center.dto.request.CenterCreateRequest;
import com.example.center.dto.response.CenterResponse;
import com.example.center.service.CenterService;

@RestController
@RequestMapping("/api/v1/companies/{id}/centers")
@RequiredArgsConstructor
public class CompanyCenterController {
    private final CenterService centerService;

    @PostMapping
    public ResponseEntity<CenterResponse> create(
        @RequestBody CenterCreateRequest center, @PathVariable Long id, @AuthenticationPrincipal JwtPayload user
    ) {
        CenterResponse response = centerService.create(center, id, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CenterResponse>> getCentersByCompanyId(@PathVariable Long id) {
        List<CenterResponse> response = centerService.getAllByCompanyId(id);
        return ResponseEntity.ok(response);
    }
}
