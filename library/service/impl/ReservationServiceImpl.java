package com.project.library.service.impl;

import com.project.library.dto.ReservationDto;
import com.project.library.exception.*;
import com.project.library.mapper.ReservationMapper;
import com.project.library.model.Book;
import com.project.library.model.Reservation;
import com.project.library.model.User;
import com.project.library.repository.BookRepository;
import com.project.library.repository.ReservationRepository;
import com.project.library.repository.UserRepository;
import com.project.library.security.CustomUserDetails;
import com.project.library.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        return reservationRepository.findAll().stream().map(reservationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ReservationDto createReservation(ReservationDto reservationDto) {
        User user = userRepository.findById(reservationDto.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Book book = bookRepository.findById(reservationDto.bookIsbn())
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
        if (book.getAvailableStock() <= 0) {
            throw new BookNotReservableException("No available stock for the book during the specified period");
        }
        Reservation reservation = reservationMapper.toModel(reservationDto, book, user);
        reservationRepository.save(reservation);
        book.setAvailableStock(book.getAvailableStock() - 1);
        bookRepository.save(book);

        return reservationMapper.toDto(reservation);
    }


    @Override
    public void deleteReservation(UUID id, CustomUserDetails principal) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        UUID reservationUserId = reservation.getUser().getId();
        UUID principalId = principal.getId();

        System.out.println("Deleting reservation: " + id);
        System.out.println("Reservation User ID: " + reservationUserId);
        System.out.println("Principal ID: " + principalId);

        if (principalId.equals(reservationUserId) || principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            Book book = reservation.getBook();
            book.setAvailableStock(book.getAvailableStock() + 1);
            bookRepository.save(book);

            reservationRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<ReservationDto> getReservationsByUserId(UUID userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        return reservations.stream().map(reservationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public boolean isReservationOwner(UUID reservationId, UUID userId) {
        return reservationRepository.findById(reservationId)
                .map(reservation -> reservation.getUser().getId().equals(userId))
                .orElse(false);
    }
}

