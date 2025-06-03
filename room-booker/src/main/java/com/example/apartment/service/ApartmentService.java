package com.example.apartment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apartment.dto.request.ApartmentCreateRequest;
import com.example.apartment.dto.request.ApartmentUpdateRequest;
import com.example.apartment.dto.response.ApartmentResponse;
import com.example.apartment.entity.ApartmentEntity;
import com.example.apartment.mapper.ApartmentEntityMapper;
import com.example.apartment.mapper.ApartmentResponseMapperFacade;
import com.example.apartment.repository.ApartmentRepository;
import com.example.auth.dto.JwtPayload;
import com.example.center.entity.CenterEntity;
import com.example.center.use_case.CenterGetIfBelongsToUser;
import com.example.common.exception.ObjectNotFoundException;
import com.example.common.exception.PermissionDeniedException;

@Service
public class ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ApartmentEntityMapper apartmentEntityMapper;
    @Autowired
    private CenterGetIfBelongsToUser centerGetIfBelongsToUser;
    @Autowired
    private ApartmentResponseMapperFacade apartmentResponseMapperFacade;

    public ApartmentResponse create(
        ApartmentCreateRequest request,
        Long centerId,
        JwtPayload jwtPayload
    ) {
        CenterEntity center = centerGetIfBelongsToUser.execute(centerId, jwtPayload.getId());

        ApartmentEntity apartment = apartmentEntityMapper.map(request, center);
        ApartmentEntity savedApartment = apartmentRepository.save(apartment);

        return apartmentResponseMapperFacade.map(savedApartment);
    }

    public ApartmentResponse update(
        Long id, 
        ApartmentUpdateRequest request,
        Long userId
    ) {
        ApartmentEntity existingApartment = getEntityById(id);
        throwIfNotApartmentOwner(existingApartment, userId);

        ApartmentEntity apartment = existingApartment.with(
            Optional.of(request.getName()),
            Optional.of(request.getNumberOfPeople()),
            Optional.of(request.getDescription()),
            Optional.of(request.getPricePerNight()),
            Optional.of(request.getAmount()),
            Optional.ofNullable(request.getFacilities()),
            Optional.empty()
        );
        ApartmentEntity updatedApartment = apartmentRepository.save(apartment);

        return apartmentResponseMapperFacade.map(updatedApartment);
    }

    public ApartmentResponse getById(Long id) {
        ApartmentEntity apartment = getEntityById(id);

        return apartmentResponseMapperFacade.map(apartment);
    }

    public List<ApartmentResponse> getByCenterId(Long centerId, Long userId) {
        CenterEntity center = centerGetIfBelongsToUser.execute(centerId, userId);

        return apartmentRepository.findByCenter(center).stream()
                .map(apartmentResponseMapperFacade::map)
                .collect(Collectors.toList());
    }

    public void delete(Long id, Long userId) {
        centerGetIfBelongsToUser.execute(
            getEntityById(id).getCenter().getId(), userId
        );

        apartmentRepository.deleteById(id);
    }

    private ApartmentEntity getEntityById(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException());
    }

    private void throwIfNotApartmentOwner(ApartmentEntity apartment, Long userId) {
        if (!apartment.getCenter().getCompany().getUser().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }
    }
}