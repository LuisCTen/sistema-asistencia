package com.semillero.asistencias_backend.service.impl;

import java.util.List;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.semillero.asistencias_backend.dto.UserRequestDto;
import com.semillero.asistencias_backend.dto.UserResponseDto;
import com.semillero.asistencias_backend.exception.BadRequestException;
import com.semillero.asistencias_backend.exception.ResourceNotFoundException;
import com.semillero.asistencias_backend.mappers.UserMapper;
import com.semillero.asistencias_backend.models.RoleEntity;
import com.semillero.asistencias_backend.models.UserEntity;
import com.semillero.asistencias_backend.repository.IRoleRepository;
import com.semillero.asistencias_backend.repository.IUserRepository;
import com.semillero.asistencias_backend.service.IUsuarioService;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUserRepository iUserRepository;
    private final IRoleRepository iRoleRepository;
    private final UserMapper iUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long buscarIdPorUsername(String username) {
        return iUserRepository.findByUsername(username)
                .map(UserEntity::getIdUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Override
    @Transactional
    public List<UserResponseDto> listarTodos() {
        List<UserEntity> entities = iUserRepository.findAll();
        return iUserMapper.toDtoList(entities);

    }

    @Override
    @Transactional
    public UserResponseDto save(UserRequestDto request) {
        if (iUserRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("El username ya existe");
        }

        if (iUserRepository.existsByUsername(request.getEmail())) {
            throw new BadRequestException("El email ya esta registrado");
        }
        RoleEntity role = iRoleRepository.findById(request.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado ID: " + request.getIdRol()));

        UserEntity entity = iUserMapper.toEntity(request, role);
        entity.setPassword(passwordEncoder.encode(request.getPassword()));

        return iUserMapper.toDto(iUserRepository.save(entity));
    }

    @Override
    @Transactional
    public UserResponseDto update(Long id, UserRequestDto request) {
        UserEntity existing = iUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado ID: " + id));

        RoleEntity role = iRoleRepository.findById(request.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado ID: " + request.getIdRol()));

        iUserMapper.updateEntity(existing, request, role);
        // Nota: EL Password no se actualiza aquí por seguridad

        return iUserMapper.toDto(iUserRepository.save(existing));
    }

    @Override
    @Transactional
    public UserResponseDto findById(Long id) {
        UserEntity entity = iUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado ID: " + id));
        return iUserMapper.toDto(entity);
    }

    @Override
    @Transactional
    public UserResponseDto delete(Long id) {
        UserEntity entity = iUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado ID: " + id));

        entity.setEstado(0);
        iUserRepository.save(entity);
        return iUserMapper.toDto(entity);
    }

}
