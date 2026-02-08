package com.example.apartment.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;

    private final ApartmentEntityMapper apartmentEntityMapper;

    private final CenterGetIfBelongsToUser centerGetIfBelongsToUser;

    private final ApartmentResponseMapperFacade apartmentResponseMapperFacade;

    public ApartmentResponse create(@NotNull ApartmentCreateRequest request, @NotNull Long centerId, @NotNull JwtPayload jwtPayload) {
        CenterEntity center = centerGetIfBelongsToUser.execute(centerId, jwtPayload.getId());

        ApartmentEntity apartment = apartmentEntityMapper.map(request, center);
        ApartmentEntity savedApartment = apartmentRepository.save(apartment);

        return apartmentResponseMapperFacade.map(savedApartment);
    }

    public ApartmentResponse update(@NotNull Long id, @NotNull ApartmentUpdateRequest request, @NotNull Long userId) {
        ApartmentEntity existingApartment = getEntityById(id);
        throwIfNotApartmentOwner(existingApartment, userId);

        ApartmentEntity apartment = existingApartment.with(
                null,
                request.getName(),
                request.getNumberOfPeople(),
                request.getDescription(),
                request.getPricePerNight(),
                request.getAmount(),
                request.getFacilities(),
                null);
        ApartmentEntity updatedApartment = apartmentRepository.save(apartment);

        return apartmentResponseMapperFacade.map(updatedApartment);
    }

    public ApartmentResponse getById(@NotNull Long id) {
        ApartmentEntity apartment = getEntityById(id);

        return apartmentResponseMapperFacade.map(apartment);
    }

    public List<ApartmentResponse> getByCenterId(@NotNull Long centerId, @NotNull Long userId) {
        CenterEntity center = centerGetIfBelongsToUser.execute(centerId, userId);

        return apartmentRepository.findByCenter(center).stream()
                .map(apartmentResponseMapperFacade::map)
                .collect(Collectors.toList());
    }

    public void delete(@NotNull Long id, @NotNull Long userId) {
        centerGetIfBelongsToUser.execute(getEntityById(id).getCenter().getId(), userId);

        apartmentRepository.deleteById(id);
    }

    private ApartmentEntity getEntityById(@NotNull Long id) {
        return apartmentRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    private void throwIfNotApartmentOwner(@NotNull ApartmentEntity apartment, @NotNull Long userId) {
        if (!apartment.getCenter().getCompany().getUser().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }
    }
}
