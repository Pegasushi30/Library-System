package com.project.library.security;

import com.project.library.dto.RegisterRequest;
import com.project.library.dto.RegisterResponse;
import com.project.library.exception.UserNotFoundException;
import com.project.library.exception.UsernameAlreadyInUseException;
import com.project.library.model.Role;
import com.project.library.model.User;
import com.project.library.repository.UserRepository;
import com.project.library.repository.RoleRepository;
import com.project.library.dto.AuthenticationRequest;
import com.project.library.dto.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("This email address is already in use");
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyInUseException("This username is already in use");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setEmail(request.email());
        user.setBirthdate(request.birthdate());
        user.setPhoneNumber(request.phoneNumber());
        user.setPassword(passwordEncoder.encode(request.password()));
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new UserNotFoundException("User role not found"));
        user.setRole(userRole);
        User savedUser = userRepository.save(user);
        String jwtToken = generateToken(savedUser);
        return new RegisterResponse(jwtToken, savedUser.getId());
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        String jwtToken = generateToken(user);
        return new AuthenticationResponse(jwtToken,user.getId());
    }
    private String generateToken(User user) {
        Map<String, Object> claims = Map.of(
                "roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
        return jwtService.generateToken(claims, user);
    }
}






