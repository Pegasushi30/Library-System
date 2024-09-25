package com.project.library.service;

import com.project.library.dto.ReservationDto;
import com.project.library.security.CustomUserDetails;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    List<ReservationDto> getAllReservations();
    ReservationDto createReservation(ReservationDto reservationDto);
    void deleteReservation(UUID id, CustomUserDetails principal);
    List<ReservationDto> getReservationsByUserId(UUID userId);
    boolean isReservationOwner(UUID reservationId, UUID userId);
}










