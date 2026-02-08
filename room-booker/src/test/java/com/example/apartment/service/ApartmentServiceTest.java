package com.example.apartment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
import com.example.company.entity.CompanyEntity;
import com.example.user.entity.UserEntity;

class ApartmentServiceTest {

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private ApartmentEntityMapper apartmentEntityMapper;

    @Mock
    private CenterGetIfBelongsToUser centerGetIfBelongsToUser;

    @Mock
    private ApartmentResponseMapperFacade apartmentResponseMapperFacade;

    @InjectMocks
    private ApartmentService apartmentService;

    private ApartmentEntity apartmentEntity;
    private ApartmentResponse apartmentResponse;
    private CenterEntity centerEntity;
    private JwtPayload jwtPayload;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UserEntity user = mock(UserEntity.class);
        given(user.getId()).willReturn(1L);

        CompanyEntity company = mock(CompanyEntity.class);
        given(company.getUser()).willReturn(user);

        centerEntity = mock(CenterEntity.class);
        given(centerEntity.getCompany()).willReturn(company);

        apartmentEntity = mock(ApartmentEntity.class);
        apartmentResponse = mock(ApartmentResponse.class);

        jwtPayload = mock(JwtPayload.class);
        given(jwtPayload.getId()).willReturn(1L);
    }

    @Test
    void shouldCreateApartment() {
        // given
        ApartmentCreateRequest request = mock(ApartmentCreateRequest.class);
        given(centerGetIfBelongsToUser.execute(10L, 1L)).willReturn(centerEntity);
        given(apartmentEntityMapper.map(request, centerEntity)).willReturn(apartmentEntity);
        given(apartmentRepository.save(apartmentEntity)).willReturn(apartmentEntity);
        given(apartmentResponseMapperFacade.map(apartmentEntity)).willReturn(apartmentResponse);

        // when
        ApartmentResponse result = apartmentService.create(request, 10L, jwtPayload);

        // then
        assertThat(result).isEqualTo(apartmentResponse);
        then(centerGetIfBelongsToUser).should(times(1)).execute(10L, 1L);
        then(apartmentEntityMapper).should(times(1)).map(request, centerEntity);
        then(apartmentRepository).should(times(1)).save(apartmentEntity);
        then(apartmentResponseMapperFacade).should(times(1)).map(apartmentEntity);
    }

    @Test
    void shouldUpdateApartment() {
        // given
        ApartmentUpdateRequest request = mock(ApartmentUpdateRequest.class);
        given(request.getName()).willReturn("Updated name");
        given(request.getNumberOfPeople()).willReturn(5);
        given(request.getDescription()).willReturn("Updated desc");
        given(request.getPricePerNight()).willReturn(200.0);
        given(request.getAmount()).willReturn(3);
        given(request.getFacilities()).willReturn(List.of());

        given(apartmentRepository.findById(1L)).willReturn(Optional.of(apartmentEntity));
        given(apartmentEntity.getCenter()).willReturn(centerEntity);

        CompanyEntity company = mock(CompanyEntity.class);
        UserEntity user = mock(UserEntity.class);
        given(centerEntity.getCompany()).willReturn(company);
        given(company.getUser()).willReturn(user);
        given(user.getId()).willReturn(1L);

        ApartmentEntity updatedApartment = mock(ApartmentEntity.class);

        given(apartmentEntity.with(null, "Updated name", 5, "Updated desc", 200.0, 3, List.of(), null))
                .willReturn(updatedApartment);

        given(apartmentRepository.save(updatedApartment)).willReturn(updatedApartment);
        given(apartmentResponseMapperFacade.map(updatedApartment)).willReturn(apartmentResponse);

        // when
        ApartmentResponse result = apartmentService.update(1L, request, 1L);

        // then
        assertThat(result).isEqualTo(apartmentResponse);
        then(apartmentEntity).should(times(1)).with(null, "Updated name", 5, "Updated desc", 200.0, 3, List.of(), null);
        then(apartmentRepository).should(times(1)).save(updatedApartment);
        then(apartmentResponseMapperFacade).should(times(1)).map(updatedApartment);
    }

    @Test
    void shouldThrowPermissionDeniedOnUpdate() {
        // given
        ApartmentUpdateRequest request = mock(ApartmentUpdateRequest.class);
        UserEntity otherUser = mock(UserEntity.class);
        given(apartmentRepository.findById(1L)).willReturn(Optional.of(apartmentEntity));
        given(apartmentEntity.getCenter()).willReturn(centerEntity);
        given(centerEntity.getCompany()).willReturn(mock(CompanyEntity.class));
        given(centerEntity.getCompany().getUser()).willReturn(otherUser);
        given(otherUser.getId()).willReturn(2L);

        // when / then
        assertThrows(PermissionDeniedException.class, () -> apartmentService.update(1L, request, 1L));
    }

    @Test
    void shouldGetApartmentById() {
        // given
        given(apartmentRepository.findById(1L)).willReturn(Optional.of(apartmentEntity));
        given(apartmentResponseMapperFacade.map(apartmentEntity)).willReturn(apartmentResponse);

        // when
        ApartmentResponse result = apartmentService.getById(1L);

        // then
        assertThat(result).isEqualTo(apartmentResponse);
        then(apartmentRepository).should(times(1)).findById(1L);
        then(apartmentResponseMapperFacade).should(times(1)).map(apartmentEntity);
    }

    @Test
    void shouldThrowObjectNotFoundWhenGetById() {
        // given
        given(apartmentRepository.findById(1L)).willReturn(Optional.empty());

        // when / then
        assertThrows(ObjectNotFoundException.class, () -> apartmentService.getById(1L));
    }

    @Test
    void shouldGetApartmentsByCenterId() {
        // given
        given(centerGetIfBelongsToUser.execute(10L, 1L)).willReturn(centerEntity);
        List<ApartmentEntity> apartments = List.of(apartmentEntity);
        given(apartmentRepository.findByCenter(centerEntity)).willReturn(apartments);
        given(apartmentResponseMapperFacade.map(apartmentEntity)).willReturn(apartmentResponse);

        // when
        List<ApartmentResponse> result = apartmentService.getByCenterId(10L, 1L);

        // then
        assertThat(result).containsExactly(apartmentResponse);
        then(centerGetIfBelongsToUser).should(times(1)).execute(10L, 1L);
        then(apartmentRepository).should(times(1)).findByCenter(centerEntity);
        then(apartmentResponseMapperFacade).should(times(1)).map(apartmentEntity);
    }

    @Test
    void shouldDeleteApartment() {
        // given
        given(apartmentRepository.findById(1L)).willReturn(Optional.of(apartmentEntity));
        given(apartmentEntity.getCenter()).willReturn(centerEntity);
        given(centerEntity.getId()).willReturn(10L);
        given(centerGetIfBelongsToUser.execute(10L, 1L)).willReturn(centerEntity);

        // when
        apartmentService.delete(1L, 1L);

        // then
        then(apartmentRepository).should(times(1)).deleteById(1L);
        then(centerGetIfBelongsToUser).should(times(1)).execute(10L, 1L);
    }
}
