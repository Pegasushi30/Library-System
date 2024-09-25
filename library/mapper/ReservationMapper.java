package com.project.library.mapper;

import com.project.library.dto.ReservationDto;
import com.project.library.model.Book;
import com.project.library.model.Reservation;
import com.project.library.model.User;
import com.project.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Component
public class ReservationMapper {

    private final UserService userService;

    @Autowired
    public ReservationMapper(UserService userService) {
        this.userService = userService;
    }

    public ReservationDto toDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getIsBorrow(),
                reservation.getBook().getIsbn(),
                reservation.getUser().getId()
        );
    }

    public Reservation toModel(ReservationDto dto, Book book, User user) {
        Reservation reservation = new Reservation();
        reservation.setId(dto.id());
        reservation.setReservationStartDate(LocalDate.now());
        reservation.setReservationEndDate(LocalDate.now().plusMonths(2));
        reservation.setIsBorrow(dto.isBorrow());
        reservation.setBook(book);
        reservation.setUser(user);
        return reservation;
    }

    public Reservation toModel(ReservationDto dto, Book book) {
        User user = userService.getUserModelById(dto.userId())
                .orElseThrow(() -> new NoSuchElementException("User not found for ID: " + dto.userId()));
        return toModel(dto, book, user);
    }
}













