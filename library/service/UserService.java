package com.project.library.service;

import com.project.library.dto.UserDto;
import com.project.library.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User saveUser(User user);
    List<UserDto> getAllUsers();
    UserDto getUserById(UUID id);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UUID id, UserDto userDto);
    void deleteUser(UUID id);
    Optional<User> getUserModelById(UUID id);
}








