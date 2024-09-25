package com.project.library.service.impl;

import com.project.library.dto.UserDto;
import com.project.library.exception.EmailAlreadyInUseException;
import com.project.library.exception.RoleMapperNotInitializedException;
import com.project.library.exception.UserNotFoundException;
import com.project.library.exception.UsernameAlreadyInUseException;
import com.project.library.mapper.UserMapper;
import com.project.library.model.User;
import com.project.library.repository.UserRepository;
import com.project.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public UserDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return userMapper.toDto(user);
    }
    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            throw new EmailAlreadyInUseException("This email address is already in use");
        }
        if (userRepository.existsByUsername(userDto.username())) {
            throw new UsernameAlreadyInUseException("This username is already in use");
        }
        User user = userMapper.toModel(userDto);
        User savedUser = saveUser(user);
        return userMapper.toDto(savedUser);
    }
    @Override
    public UserDto updateUser(UUID id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.setUsername(userDto.username());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setFirstname(userDto.firstname());
        user.setLastname(userDto.lastname());
        user.setEmail(userDto.email());
        user.setPhoneNumber(userDto.phoneNumber());
        user.setBirthdate(userDto.birthdate());
        if (userMapper.getRoleMapper() != null) {
            user.setRole(userMapper.getRoleMapper().toModel(userDto.role()));
        } else {
            throw new RoleMapperNotInitializedException("RoleMapper is not initialized");
        }

        User updatedUser = saveUser(user);
        return userMapper.toDto(updatedUser);
    }
    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
    @Override
    public Optional<User> getUserModelById(UUID id) {
        return userRepository.findById(id);
    }
}

