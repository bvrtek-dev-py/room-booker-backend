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
import com.example.reservation.dto.request.ReservationCreateRequest;
import com.example.reservation.entity.ReservationEntity;
import com.example.user.entity.UserEntity;
import com.example.user.role.UserRole;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReservationEntityMapper Tests")
public class ReservationEntityMapperTest {

    @InjectMocks
    private ReservationEntityMapper reservationEntityMapper;

    @Test
    @DisplayName("GIVEN valid request, user and apartment WHEN mapping to entity THEN should create reservation entity with correct price")
    public void testMapToEntity_WithValidData_CreatesEntityWithCorrectPrice() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);
        ReservationCreateRequest createRequest = new ReservationCreateRequest(1L, checkInDate, checkOutDate, 2);
        UserEntity testUser = new UserEntity(1L, "testuser", "password", "test@test.com", UserRole.USER);
        ApartmentEntity testApartment = new ApartmentEntity(1L, "Test Apartment", 4, "Description", 100.0, 2, List.of(Facility.WIFI), null);

        // when
        ReservationEntity result = reservationEntityMapper.map(createRequest, testUser, testApartment);

        // then
        assertNotNull(result);
        assertNotNull(result.getUser());
        assertNotNull(result.getApartment());
        assertEquals(testUser.getId(), result.getUser().getId());
        assertEquals(testApartment.getId(), result.getApartment().getId());
        assertEquals(checkInDate, result.getCheckInDate());
        assertEquals(checkOutDate, result.getCheckOutDate());
        assertEquals(2, result.getNumberOfGuests());
        assertEquals(500.0, result.getTotalPrice());
    }
}
