package com.project.library.service;

import com.project.library.dto.ReservationDto;
import com.project.library.exception.*;
import com.project.library.security.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID; // UUID import

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationService reservationService;
    private ReservationDto reservationDto;
    private CustomUserDetails principal;
    private UUID reservationId; // Use UUID for reservation ID
    private UUID userId; // Use UUID for user ID

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationId = UUID.randomUUID(); // Generate random UUID for reservationId
        userId = UUID.randomUUID();   // Generate random UUID for userId
        reservationDto = new ReservationDto(reservationId, true, 123, userId); // Use UUID for userId
        principal = mock(CustomUserDetails.class);
    }

    @Test
    void testGetAllReservations() {
        // Given
        when(reservationService.getAllReservations()).thenReturn(Collections.singletonList(reservationDto));

        // When
        List<ReservationDto> reservations = reservationService.getAllReservations();

        // Then
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals(reservationDto, reservations.get(0));
        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void testCreateReservation() {
        // Given
        when(reservationService.createReservation(any(ReservationDto.class))).thenReturn(reservationDto);

        // When
        ReservationDto createdReservation = reservationService.createReservation(reservationDto);

        // Then
        assertNotNull(createdReservation);
        assertEquals(reservationDto, createdReservation);
        verify(reservationService, times(1)).createReservation(any(ReservationDto.class));
    }

    @Test
    void testCreateReservation_BookNotReservable() {
        // Given
        doThrow(new BookNotReservableException("No available stock for the book during the specified period"))
                .when(reservationService).createReservation(any(ReservationDto.class));

        // When / Then
        assertThrows(BookNotReservableException.class, () -> reservationService.createReservation(reservationDto));

        verify(reservationService, times(1)).createReservation(any(ReservationDto.class));
    }

    @Test
    void testDeleteReservation() {
        // Given
        doNothing().when(reservationService).deleteReservation(any(UUID.class), any(CustomUserDetails.class)); // Use UUID

        // When
        reservationService.deleteReservation(reservationId, principal); // Pass UUID

        // Then
        verify(reservationService, times(1)).deleteReservation(any(UUID.class), any(CustomUserDetails.class));
    }

    @Test
    void testDeleteReservation_AccessDenied() {
        // Given
        doThrow(new AccessDeniedException("Access denied")).when(reservationService).deleteReservation(any(UUID.class), any(CustomUserDetails.class)); // Use UUID

        // When / Then
        assertThrows(AccessDeniedException.class, () -> reservationService.deleteReservation(reservationId, principal)); // Use UUID

        verify(reservationService, times(1)).deleteReservation(any(UUID.class), any(CustomUserDetails.class));
    }

    @Test
    void testGetReservationsByUserId() {
        // Given
        when(reservationService.getReservationsByUserId(userId)).thenReturn(Collections.singletonList(reservationDto));

        // When
        List<ReservationDto> reservations = reservationService.getReservationsByUserId(userId);

        // Then
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals(reservationDto, reservations.get(0));
        verify(reservationService, times(1)).getReservationsByUserId(userId);
    }

    @Test
    void testIsReservationOwner() {
        // Given
        when(reservationService.isReservationOwner(any(UUID.class), any(UUID.class))).thenReturn(true); // Use UUID for both

        // When
        boolean isOwner = reservationService.isReservationOwner(reservationId, userId); // Use UUID

        // Then
        assertTrue(isOwner);
        verify(reservationService, times(1)).isReservationOwner(any(UUID.class), any(UUID.class));
    }

    @Test
    void testIsReservationOwner_False() {
        // Given
        when(reservationService.isReservationOwner(any(UUID.class), any(UUID.class))).thenReturn(false); // Use UUID for both

        // When
        boolean isOwner = reservationService.isReservationOwner(reservationId, userId); // Use UUID

        // Then
        assertFalse(isOwner);
        verify(reservationService, times(1)).isReservationOwner(any(UUID.class), any(UUID.class));
    }

    @Test
    void testDeleteReservation_NotFound() {
        // Given
        doThrow(new ReservationNotFoundException("Reservation not found")).when(reservationService).deleteReservation(any(UUID.class), any(CustomUserDetails.class)); // Use UUID

        // When / Then
        assertThrows(ReservationNotFoundException.class, () -> reservationService.deleteReservation(reservationId, principal)); // Use UUID

        verify(reservationService, times(1)).deleteReservation(any(UUID.class), any(CustomUserDetails.class));
    }

    @Test
    void testCreateReservation_UserNotFound() {
        // Given
        doThrow(new UserNotFoundException("User not found")).when(reservationService).createReservation(any(ReservationDto.class));

        // When / Then
        assertThrows(UserNotFoundException.class, () -> reservationService.createReservation(reservationDto));

        verify(reservationService, times(1)).createReservation(any(ReservationDto.class));
    }

    @Test
    void testCreateReservation_BookNotFound() {
        // Given
        doThrow(new BookNotFoundException("Book not found")).when(reservationService).createReservation(any(ReservationDto.class));

        // When / Then
        assertThrows(BookNotFoundException.class, () -> reservationService.createReservation(reservationDto));

        verify(reservationService, times(1)).createReservation(any(ReservationDto.class));
    }
}

