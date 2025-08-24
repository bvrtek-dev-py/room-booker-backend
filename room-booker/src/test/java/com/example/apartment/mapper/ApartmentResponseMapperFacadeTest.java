package com.example.apartment.mapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import org.mockito.MockitoAnnotations;

import com.example.apartment.dto.response.ApartmentResponse;
import com.example.apartment.entity.ApartmentEntity;
import com.example.apartment.type.Facility;
import com.example.center.dto.response.CenterResponse;
import com.example.center.entity.CenterEntity;
import com.example.center.mapper.CenterResponseMapperFacade;

class ApartmentResponseMapperFacadeTest {

    @Mock
    private ApartmentResponseMapper apartmentResponseMapper;

    @Mock
    private CenterResponseMapperFacade centerResponseMapperFacade;

    @InjectMocks
    private ApartmentResponseMapperFacade apartmentResponseMapperFacade;

    private ApartmentEntity apartmentEntity;
    private CenterEntity centerEntity;
    private CenterResponse centerResponse;
    private ApartmentResponse expectedResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        centerEntity = mock(CenterEntity.class);

        apartmentEntity = new ApartmentEntity(
                1L,
                "Luxury Apartment",
                4,
                "Nice place",
                120.0,
                3,
                List.of(Facility.WIFI, Facility.PARKING),
                centerEntity
        );

        centerResponse = new CenterResponse(10L, "Main Center", "Opis", null, null);

        expectedResponse = new ApartmentResponse(
                1L,
                "Luxury Apartment",
                4,
                "Nice place",
                120.0,
                3,
                List.of(Facility.WIFI, Facility.PARKING),
                centerResponse
        );
    }

    @Test
    void shouldMapApartmentEntityToApartmentResponse() {
        // given
        given(centerResponseMapperFacade.map(centerEntity, null)).willReturn(centerResponse);
        given(apartmentResponseMapper.map(apartmentEntity, centerResponse)).willReturn(expectedResponse);

        // when
        ApartmentResponse result = apartmentResponseMapperFacade.map(apartmentEntity);

        // then
        assertThat(result).isEqualTo(expectedResponse);

        verify(centerResponseMapperFacade, times(1)).map(centerEntity, null);
        verify(apartmentResponseMapper, times(1)).map(apartmentEntity, centerResponse);
        verifyNoMoreInteractions(centerResponseMapperFacade, apartmentResponseMapper);
    }
}
