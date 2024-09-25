package com.project.library.service;

import com.project.library.dto.RoleDto;
import com.project.library.mapper.RoleMapper;
import com.project.library.model.Role;
import com.project.library.repository.RoleRepository;
import com.project.library.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID; // UUID için import eklendi

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    private UUID roleId; // UUID eklendi

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleId = UUID.randomUUID(); // UUID kullanımı
    }

    @Test
    void testGetAllRoleDto() {
        Role role = new Role();
        role.setId(roleId); // UUID kullanımı
        role.setRoleName("USER");

        RoleDto roleDto = new RoleDto(roleId, "USER"); // UUID kullanımı

        when(roleRepository.findAll()).thenReturn(Collections.singletonList(role));
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        List<RoleDto> roles = roleService.getAllRoles();

        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals(roleDto, roles.get(0));
        verify(roleRepository, times(1)).findAll();
        verify(roleMapper, times(1)).toDto(role);
    }

    @Test
    void testGetRoleDtoById() {
        Role role = new Role();
        role.setId(roleId); // UUID kullanımı
        role.setRoleName("USER");

        RoleDto roleDto = new RoleDto(roleId, "USER"); // UUID kullanımı

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        Optional<RoleDto> foundRole = roleService.getRoleById(roleId);

        assertTrue(foundRole.isPresent());
        assertEquals(roleDto, foundRole.get());
        verify(roleRepository, times(1)).findById(roleId); // UUID kullanımı
        verify(roleMapper, times(1)).toDto(role);
    }

    @Test
    void testCreateRole() {
        RoleDto roleDto = new RoleDto(roleId, "USER"); // UUID kullanımı
        Role role = new Role();
        role.setId(roleId); // UUID kullanımı
        role.setRoleName("USER");

        when(roleMapper.toModel(roleDto)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        RoleDto createdRole = roleService.createRole(roleDto);

        assertNotNull(createdRole);
        assertEquals(roleDto, createdRole);
        verify(roleMapper, times(1)).toModel(roleDto);
        verify(roleRepository, times(1)).save(role);
        verify(roleMapper, times(1)).toDto(role);
    }

    @Test
    void testUpdateRole() {
        RoleDto roleDto = new RoleDto(roleId, "ADMIN"); // UUID kullanımı
        Role role = new Role();
        role.setId(roleId); // UUID kullanımı
        role.setRoleName("USER");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        RoleDto updatedRole = roleService.updateRole(roleId, roleDto);

        assertNotNull(updatedRole);
        assertEquals(roleDto, updatedRole);
        verify(roleRepository, times(1)).findById(roleId); // UUID kullanımı
        verify(roleRepository, times(1)).save(role);
        verify(roleMapper, times(1)).toDto(role);
    }

    @Test
    void testDeleteRole() {
        roleService.deleteRole(roleId);
        verify(roleRepository, times(1)).deleteById(roleId); // UUID kullanımı
    }
}