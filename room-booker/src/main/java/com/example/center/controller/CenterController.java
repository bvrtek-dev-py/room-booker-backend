package com.example.center.controller;

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

import com.example.auth.dto.JwtPayload;
import com.example.center.dto.request.CenterUpdateRequest;
import com.example.center.dto.response.CenterResponse;
import com.example.center.service.CenterService;

@RestController
@RequestMapping("/api/v1/centers")
public class CenterController {
    @Autowired
    private CenterService centerService;

    @GetMapping("/{id}")
    public ResponseEntity<CenterResponse> getById(@PathVariable Long id) {
        CenterResponse response = centerService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CenterResponse> update(
            @PathVariable Long id, @RequestBody CenterUpdateRequest request, @AuthenticationPrincipal JwtPayload user) {
        CenterResponse response = centerService.update(id, request, user.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal JwtPayload user) {
        centerService.delete(id, user.getId());

        return ResponseEntity.noContent().build();
    }
}
