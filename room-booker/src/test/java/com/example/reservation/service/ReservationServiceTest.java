package com.example.reservation.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.example.apartment.entity.ApartmentEntity;
import com.example.apartment.repository.ApartmentRepository;
import com.example.apartment.type.Facility;
import com.example.center.entity.CenterEntity;
import com.example.center.repository.CenterRepository;
import com.example.common.exception.ObjectNotFoundException;
import com.example.common.exception.PermissionDeniedException;
import com.example.reservation.dto.request.ReservationCreateRequest;
import com.example.reservation.dto.response.AvailableApartmentResponse;
import com.example.reservation.dto.response.ReservationResponse;
import com.example.reservation.entity.ReservationEntity;
import com.example.reservation.exception.ApartmentNotAvailableException;
import com.example.reservation.exception.GuestLimitExceededException;
import com.example.reservation.exception.ReservationNotFoundException;
import com.example.reservation.mapper.AvailableApartmentResponseMapper;
import com.example.reservation.mapper.ReservationEntityMapper;
import com.example.reservation.mapper.ReservationResponseMapper;
import com.example.reservation.repository.ReservationRepository;
import com.example.user.entity.UserEntity;
import com.example.user.repository.UserRepository;
import com.example.user.role.UserRole;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReservationService Tests")
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReservationEntityMapper reservationEntityMapper;

    @Mock
    private ReservationResponseMapper reservationResponseMapper;

    @Mock
    private CenterRepository centerRepository;

    @Mock
    private AvailableApartmentResponseMapper availableApartmentResponseMapper;

    @InjectMocks
    private ReservationService reservationService;

    // ==================== CREATE RESERVATION TESTS ====================

    @Test
    @DisplayName("GIVEN valid reservation request WHEN creating reservation THEN should return reservation response")
    public void testCreateReservation_WithValidRequest_Success() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);
        UserEntity testUser = new UserEntity(1L, "testuser", "password", "test@test.com", UserRole.USER);
        ApartmentEntity testApartment = new ApartmentEntity(1L, "Test Apartment", 4, "Description", 100.0, 2, List.of(Facility.WIFI), null);
        ReservationCreateRequest request = new ReservationCreateRequest(1L, checkInDate, checkOutDate, 2);
        ReservationEntity reservationEntity = new ReservationEntity(1L, testUser, testApartment, checkInDate, checkOutDate, 2, 500.0);
        ReservationResponse response = new ReservationResponse(1L, 1L, 1L, "Test Apartment", checkInDate, checkOutDate, 2, 500.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(testApartment));
        when(reservationRepository.countInDateRange(any(Specification.class))).thenReturn(0L);
        when(reservationEntityMapper.map(request, testUser, testApartment)).thenReturn(reservationEntity);
        when(reservationRepository.save(reservationEntity)).thenReturn(reservationEntity);
        when(reservationResponseMapper.map(reservationEntity)).thenReturn(response);

        // when
        ReservationResponse result = reservationService.create(request, 1L);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getUserId());
        verify(userRepository).findById(1L);
        verify(reservationRepository).save(any(ReservationEntity.class));
    }

    @Test
    @DisplayName("GIVEN non-existent user WHEN creating reservation THEN should throw ObjectNotFoundException")
    public void testCreateReservation_WithNonExistentUser_ThrowsException() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);
        ReservationCreateRequest request = new ReservationCreateRequest(1L, checkInDate, checkOutDate, 2);

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(ObjectNotFoundException.class, () -> reservationService.create(request, 999L));
        verify(userRepository).findById(999L);
    }

    @Test
    @DisplayName("GIVEN non-existent apartment WHEN creating reservation THEN should throw ObjectNotFoundException")
    public void testCreateReservation_WithNonExistentApartment_ThrowsException() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);
        ReservationCreateRequest request = new ReservationCreateRequest(1L, checkInDate, checkOutDate, 2);
        UserEntity testUser = new UserEntity(1L, "testuser", "password", "test@test.com", UserRole.USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(apartmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(ObjectNotFoundException.class, () -> reservationService.create(request, 1L));
        verify(apartmentRepository).findById(1L);
    }

    @Test
    @DisplayName("GIVEN all rooms are reserved WHEN creating reservation THEN should throw ApartmentNotAvailableException")
    public void testCreateReservation_WithNoAvailableRooms_ThrowsException() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);
        ReservationCreateRequest request = new ReservationCreateRequest(1L, checkInDate, checkOutDate, 2);
        UserEntity testUser = new UserEntity(1L, "testuser", "password", "test@test.com", UserRole.USER);
        ApartmentEntity testApartment = new ApartmentEntity(1L, "Test Apartment", 4, "Description", 100.0, 2, List.of(Facility.WIFI), null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(testApartment));
        when(reservationRepository.countInDateRange(any(Specification.class))).thenReturn(2L);

        // when
        ApartmentNotAvailableException exception = assertThrows(ApartmentNotAvailableException.class,
                () -> reservationService.create(request, 1L));
        assertEquals("Apartment is not available for the selected dates", exception.getMessage());
    }

    @Test
    @DisplayName("GIVEN guest count exceeds apartment capacity WHEN creating reservation THEN should throw GuestLimitExceededException")
    public void testCreateReservation_WithTooManyGuests_ThrowsException() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);
        ReservationCreateRequest request = new ReservationCreateRequest(1L, checkInDate, checkOutDate, 5);
        UserEntity testUser = new UserEntity(1L, "testuser", "password", "test@test.com", UserRole.USER);
        ApartmentEntity testApartment = new ApartmentEntity(1L, "Test Apartment", 4, "Description", 100.0, 2, List.of(Facility.WIFI), null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(testApartment));
        when(reservationRepository.countInDateRange(any(Specification.class))).thenReturn(0L);

        // when
        GuestLimitExceededException exception = assertThrows(GuestLimitExceededException.class,
                () -> reservationService.create(request, 1L));
        assertEquals("Number of guests exceeds apartment capacity", exception.getMessage());
    }

    // ==================== GET BY USER TESTS ====================

    @Test
    @DisplayName("GIVEN valid user ID WHEN getting user reservations THEN should return list of reservations")
    public void testGetByUser_WithValidUserId_ReturnsReservations() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);
        UserEntity testUser = new UserEntity(1L, "testuser", "password", "test@test.com", UserRole.USER);
        ApartmentEntity testApartment = new ApartmentEntity(1L, "Test Apartment", 4, "Description", 100.0, 2, List.of(Facility.WIFI), null);

        ReservationEntity reservation1 = new ReservationEntity(1L, testUser, testApartment, checkInDate, checkOutDate, 2, 500.0);
        ReservationEntity reservation2 = new ReservationEntity(2L, testUser, testApartment, checkInDate, checkOutDate, 2, 500.0);
        List<ReservationEntity> reservations = Arrays.asList(reservation1, reservation2);

        ReservationResponse response1 = new ReservationResponse(1L, 1L, 1L, "Test Apartment", checkInDate, checkOutDate, 2, 500.0);
        ReservationResponse response2 = new ReservationResponse(2L, 1L, 1L, "Test Apartment", checkInDate, checkOutDate, 2, 500.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(reservationRepository.findByUser(testUser)).thenReturn(reservations);
        when(reservationResponseMapper.map(reservation1)).thenReturn(response1);
        when(reservationResponseMapper.map(reservation2)).thenReturn(response2);

        // when
        List<ReservationResponse> result = reservationService.getByUser(1L);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reservationRepository).findByUser(testUser);
    }

    @Test
    @DisplayName("GIVEN non-existent user WHEN getting user reservations THEN should throw ObjectNotFoundException")
    public void testGetByUser_WithNonExistentUser_ThrowsException() {
        // given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(ObjectNotFoundException.class, () -> reservationService.getByUser(999L));
    }

    @Test
    @DisplayName("GIVEN user with no reservations WHEN getting user reservations THEN should return empty list")
    public void testGetByUser_WithNoReservations_ReturnsEmptyList() {
        // given
        UserEntity testUser = new UserEntity(1L, "testuser", "password", "test@test.com", UserRole.USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(reservationRepository.findByUser(testUser)).thenReturn(List.of());

        // when
        List<ReservationResponse> result = reservationService.getByUser(1L);

        // then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    // ==================== GET BY APARTMENT TESTS ====================

    @Test
    @DisplayName("GIVEN valid apartment ID WHEN getting apartment reservations THEN should return list of reservations")
    public void testGetByApartment_WithValidApartmentId_ReturnsReservations() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);
        UserEntity testUser = new UserEntity(1L, "testuser", "password", "test@test.com", UserRole.USER);
        ApartmentEntity testApartment = new ApartmentEntity(1L, "Test Apartment", 4, "Description", 100.0, 2, List.of(Facility.WIFI), null);

        ReservationEntity reservation = new ReservationEntity(1L, testUser, testApartment, checkInDate, checkOutDate, 2, 500.0);
        ReservationResponse response = new ReservationResponse(1L, 1L, 1L, "Test Apartment", checkInDate, checkOutDate, 2, 500.0);

        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(testApartment));
        when(reservationRepository.findByApartment(testApartment)).thenReturn(List.of(reservation));
        when(reservationResponseMapper.map(reservation)).thenReturn(response);

        // when
        List<ReservationResponse> result = reservationService.getByApartment(1L);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    @DisplayName("GIVEN non-existent apartment WHEN getting apartment reservations THEN should throw ObjectNotFoundException")
    public void testGetByApartment_WithNonExistentApartment_ThrowsException() {
        // given
        when(apartmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(ObjectNotFoundException.class, () -> reservationService.getByApartment(999L));
    }

    // ==================== GET AVAILABLE APARTMENTS TESTS ====================

    @Test
    @DisplayName("GIVEN valid center ID and dates WHEN getting available apartments THEN should return list of available apartments")
    public void testGetAvailableApartments_WithValidData_ReturnsAvailableApartments() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);

        CenterEntity testCenter = new CenterEntity(1L, "Test Center", "Description", null);
        ApartmentEntity testApartment = new ApartmentEntity(1L, "Test Apartment", 4, "Description", 100.0, 2, List.of(Facility.WIFI), testCenter);
        AvailableApartmentResponse availableResponse = new AvailableApartmentResponse(
                1L, "Test Apartment", 4, "Test", 100.0, List.of(), checkInDate, checkOutDate, 500.0, 2, 0, 2);

        when(centerRepository.findById(1L)).thenReturn(Optional.of(testCenter));
        when(apartmentRepository.findByCenter(testCenter)).thenReturn(List.of(testApartment));
        when(reservationRepository.count(any(Specification.class))).thenReturn(0L);
        when(availableApartmentResponseMapper.map(testApartment, checkInDate, checkOutDate, 2, 0, 2))
                .thenReturn(availableResponse);

        // when
        List<AvailableApartmentResponse> result = reservationService.getAvailableApartments(1L, checkInDate, checkOutDate);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(centerRepository).findById(1L);
    }

    @Test
    @DisplayName("GIVEN non-existent center WHEN getting available apartments THEN should throw ObjectNotFoundException")
    public void testGetAvailableApartments_WithNonExistentCenter_ThrowsException() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);

        when(centerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(ObjectNotFoundException.class,
                () -> reservationService.getAvailableApartments(999L, checkInDate, checkOutDate));
    }

    @Test
    @DisplayName("GIVEN all apartments are fully booked WHEN getting available apartments THEN should return empty list")
    public void testGetAvailableApartments_WithAllApartmentsBooked_ReturnsEmptyList() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);

        CenterEntity testCenter = new CenterEntity(1L, "Test Center", "Description", null);
        ApartmentEntity testApartment = new ApartmentEntity(1L, "Test Apartment", 4, "Description", 100.0, 1, List.of(Facility.WIFI), testCenter);

        when(centerRepository.findById(1L)).thenReturn(Optional.of(testCenter));
        when(apartmentRepository.findByCenter(testCenter)).thenReturn(List.of(testApartment));
        when(reservationRepository.count(any(Specification.class))).thenReturn(1L);

        // when
        List<AvailableApartmentResponse> result = reservationService.getAvailableApartments(1L, checkInDate, checkOutDate);

        // then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    // ==================== CANCEL RESERVATION TESTS ====================

    @Test
    @DisplayName("GIVEN valid reservation and owner user WHEN canceling reservation THEN should delete reservation")
    public void testCancelReservation_WithValidOwner_Success() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);
        UserEntity testUser = new UserEntity(1L, "testuser", "password", "test@test.com", UserRole.USER);
        ApartmentEntity testApartment = new ApartmentEntity(1L, "Test Apartment", 4, "Description", 100.0, 2, List.of(Facility.WIFI), null);
        ReservationEntity reservation = new ReservationEntity(1L, testUser, testApartment, checkInDate, checkOutDate, 2, 500.0);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        // when
        reservationService.cancel(1L, 1L);

        // then
        verify(reservationRepository).findById(1L);
        verify(reservationRepository).deleteById(1L);
    }

    @Test
    @DisplayName("GIVEN non-existent reservation WHEN canceling reservation THEN should throw ReservationNotFoundException")
    public void testCancelReservation_WithNonExistentReservation_ThrowsException() {
        // given
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(ReservationNotFoundException.class, () -> reservationService.cancel(999L, 1L));
    }

    @Test
    @DisplayName("GIVEN user who is not reservation owner WHEN canceling reservation THEN should throw PermissionDeniedException")
    public void testCancelReservation_WithDifferentUser_ThrowsException() {
        // given
        LocalDate checkInDate = LocalDate.of(2026, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2026, 2, 15);
        UserEntity testUser = new UserEntity(1L, "testuser", "password", "test@test.com", UserRole.USER);
        ApartmentEntity testApartment = new ApartmentEntity(1L, "Test Apartment", 4, "Description", 100.0, 2, List.of(Facility.WIFI), null);
        ReservationEntity reservation = new ReservationEntity(1L, testUser, testApartment, checkInDate, checkOutDate, 2, 500.0);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        // when
        assertThrows(PermissionDeniedException.class, () -> reservationService.cancel(1L, 2L));
        verify(reservationRepository, times(0)).deleteById(anyLong());
    }
}
