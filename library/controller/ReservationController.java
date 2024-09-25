package com.project.library.controller;

import com.project.library.dto.ReservationDto;
import com.project.library.security.CustomUserDetails;
import com.project.library.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private static final Logger logger = Logger.getLogger(ReservationController.class.getName());
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<ReservationDto> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.id")
    @GetMapping("/user/{userId}")
    public List<ReservationDto> getReservationsByUserId(@PathVariable UUID userId, @AuthenticationPrincipal CustomUserDetails principal) {
        logger.info("Getting reservations for user id: " + principal.getId());
        return reservationService.getReservationsByUserId(userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #reservationDto.userId == principal.id")
    @PostMapping
    public ReservationDto createReservation(@RequestBody ReservationDto reservationDto, @AuthenticationPrincipal CustomUserDetails principal) {
        logger.info("Creating reservation for user id: " + principal.getId());
        return reservationService.createReservation(reservationDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @reservationServiceImpl.isReservationOwner(#id, principal.id)")
    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable UUID id, @AuthenticationPrincipal CustomUserDetails principal) {
        logger.info("Deleting reservation for user id: " + principal.getId());
        reservationService.deleteReservation(id, principal);
    }
}











