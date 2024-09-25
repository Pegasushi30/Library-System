package com.project.library.service.impl;

import com.project.library.dto.RoleDto;
import com.project.library.mapper.RoleMapper;
import com.project.library.model.Role;
import com.project.library.repository.RoleRepository;
import com.project.library.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDto> getRoleById(UUID id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDto);
    }

    @Override
    public RoleDto createRole(RoleDto roleDTO) {
        Role role = roleMapper.toModel(roleDTO);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public RoleDto updateRole(UUID id, RoleDto roleDto) {
        Role role = roleRepository.findById(id).orElseThrow();
        role.setRoleName(roleDto.roleName());
        Role updatedRole = roleRepository.save(role);
        return roleMapper.toDto(updatedRole);
    }

    @Override
    public void deleteRole(UUID id) {
        roleRepository.deleteById(id);
    }
}