package com.project.library.service;

import com.project.library.dto.RoleDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {
    List<RoleDto> getAllRoles();
    Optional<RoleDto> getRoleById(UUID id);
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(UUID id, RoleDto roleDto);
    void deleteRole(UUID id);
}

