package com.example.apartment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apartment.dto.request.ApartmentCreateRequest;
import com.example.apartment.dto.request.ApartmentUpdateRequest;
import com.example.apartment.dto.response.ApartmentResponse;
import com.example.apartment.entity.ApartmentEntity;
import com.example.apartment.mapper.ApartmentEntityMapper;
import com.example.apartment.mapper.ApartmentResponseMapper;
import com.example.apartment.repository.ApartmentRepository;
import com.example.common.exception.ObjectNotFoundException;

@Service
public class ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ApartmentEntityMapper apartmentEntityMapper;
    @Autowired
    private ApartmentResponseMapper apartmentResponseMapper;

    public ApartmentResponse create(ApartmentCreateRequest request) {
        ApartmentEntity apartment = apartmentEntityMapper.map(request);
        ApartmentEntity savedApartment = apartmentRepository.save(apartment);

        return apartmentResponseMapper.map(savedApartment);
    }

    public ApartmentResponse update(Long id, ApartmentUpdateRequest request) {
        this.getById(id);

        ApartmentEntity apartment = apartmentEntityMapper.map(request, id);
        ApartmentEntity updatedApartment = apartmentRepository.save(apartment);

        return apartmentResponseMapper.map(updatedApartment);
    }

    public ApartmentResponse getById(Long id) {
        ApartmentEntity apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException());

        return apartmentResponseMapper.map(apartment);
    }

    public List<ApartmentResponse> getAll() {
        return apartmentRepository.findAll().stream()
                .map(apartmentResponseMapper::map)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        this.getById(id);

        apartmentRepository.deleteById(id);
    }
}