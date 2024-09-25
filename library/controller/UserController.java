package com.project.library.controller;


import com.project.library.dto.UserDto;
import com.project.library.security.CustomUserDetails;
import com.project.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class UserController {

    private static final Logger logger = Logger.getLogger(ReservationController.class.getName());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.id")
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable UUID userId, @AuthenticationPrincipal CustomUserDetails principal) {
        logger.info("Getting info  for user id: " + principal.getId());
        return userService.getUserById(userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}


