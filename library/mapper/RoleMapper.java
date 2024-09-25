package com.project.library.mapper;

import com.project.library.dto.RoleDto;
import com.project.library.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toDto(Role role) {
        return new RoleDto(role.getId(), role.getRoleName());
    }

    public Role toModel(RoleDto roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.id());
        role.setRoleName(roleDTO.roleName());
        return role;
    }
}


