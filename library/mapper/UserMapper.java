package com.project.library.mapper;

import com.project.library.dto.UserDto;
import com.project.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final RoleMapper roleMapper;

    @Autowired
    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBirthdate(),
                roleMapper.toDto(user.getRole())
        );
    }

    public User toModel(UserDto dto) {
        User user = new User();
        user.setId(dto.id());
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setFirstname(dto.firstname());
        user.setLastname(dto.lastname());
        user.setEmail(dto.email());
        user.setPhoneNumber(dto.phoneNumber());
        user.setBirthdate(dto.birthdate());
        user.setRole(roleMapper.toModel(dto.role()));
        return user;
    }
}






