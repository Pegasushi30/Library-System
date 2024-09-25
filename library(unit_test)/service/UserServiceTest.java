package com.project.library.service;

import com.project.library.dto.RoleDto;
import com.project.library.dto.UserDto;
import com.project.library.exception.EmailAlreadyInUseException;
import com.project.library.exception.UserNotFoundException;
import com.project.library.mapper.RoleMapper;
import com.project.library.mapper.UserMapper;
import com.project.library.model.User;
import com.project.library.repository.UserRepository;
import com.project.library.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleMapper roleMapper; // RoleMapper is now used

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService; // Use the implementation class

    private UserDto userDto;
    private User user;
    private UUID userId; // UUID for userId

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID(); // Generate random UUID for userId
        userDto = new UserDto(userId, "username", "password", "firstName", "lastName", "email", "phoneNumber", "birthdate", new RoleDto(UUID.randomUUID(), "ADMIN"));
        user = new User();
        user.setId(userId);
        user.setUsername("username");
        user.setPassword("encodedPassword");
    }

    @Test
    void testCreateUser() {
        // Given
        when(userMapper.toModel(userDto)).thenReturn(user);
        when(passwordEncoder.encode(userDto.password())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        UserDto createdUser = userService.createUser(userDto);

        // Then
        assertEquals(userDto, createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUser_EmailAlreadyInUse() {
        // Given
        when(userRepository.existsByEmail(userDto.email())).thenReturn(true);

        // When
        EmailAlreadyInUseException exception = assertThrows(EmailAlreadyInUseException.class, () -> userService.createUser(userDto));

        // Then
        assertEquals("This email address is already in use", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(userDto.email());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testGetUserById() {
        // Given
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        UserDto foundUser = userService.getUserById(userId);

        // Then
        assertEquals(userDto, foundUser);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserById_NotFound() {
        // Given
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When / Then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteUser() {
        // Given
        doNothing().when(userRepository).deleteById(userId);

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_NotFound() {
        // Given
        doThrow(new UserNotFoundException("User not found with id: " + userId)).when(userRepository).deleteById(userId);

        // When / Then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));

        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testUpdateUser() {
        // Given
        when(userMapper.getRoleMapper()).thenReturn(roleMapper); // Ensure the RoleMapper is injected here
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(userDto.password())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        UserDto updatedUser = userService.updateUser(userId, userDto);

        // Then
        assertNotNull(updatedUser);
        assertEquals(userDto, updatedUser);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode(userDto.password());
    }

    @Test
    void testUpdateUser_NotFound() {
        // Given
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When / Then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, userDto));

        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).save(any(User.class));
        verify(passwordEncoder, times(0)).encode(anyString());
    }
}


