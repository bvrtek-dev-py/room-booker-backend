package com.example.reservation.mapper;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.apartment.entity.ApartmentEntity;
import com.example.apartment.type.Facility;
import com.example.reservation.dto.response.ReservationResponse;
import com.example.reservation.entity.ReservationEntity;
import com.example.user.entity.UserEntity;
import com.example.user.role.UserRole;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReservationResponseMapper Tests")
public class ReservationResponseMapperTest {

    @InjectMocks
    private ReservationResponseMapper reservationResponseMapper;

    @Test
    @DisplayName("GIVEN valid reservation entity WHEN mapping to response THEN should return response with all fields")
    public void testMapToResponse_WithValidEntity_ReturnsResponseWithAllFields() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);
        UserEntity testUser = new UserEntity(1L, "testuser", "password", "test@test.com", UserRole.USER);
        ApartmentEntity testApartment = new ApartmentEntity(1L, "Test Apartment", 4, "Description", 100.0, 2, List.of(Facility.WIFI), null);
        ReservationEntity reservationEntity = new ReservationEntity(1L, testUser, testApartment, checkInDate, checkOutDate, 2, 500.0);

        // when
        ReservationResponse result = reservationResponseMapper.map(reservationEntity);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getUserId());
        assertEquals(1L, result.getApartmentId());
        assertEquals("Test Apartment", result.getApartmentName());
        assertEquals(checkInDate, result.getCheckInDate());
        assertEquals(checkOutDate, result.getCheckOutDate());
        assertEquals(2, result.getNumberOfGuests());
        assertEquals(500.0, result.getTotalPrice());
    }
}
