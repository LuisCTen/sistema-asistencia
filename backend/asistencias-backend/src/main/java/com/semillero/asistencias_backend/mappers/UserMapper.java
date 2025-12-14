package com.semillero.asistencias_backend.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.semillero.asistencias_backend.dto.UserRequestDto;
import com.semillero.asistencias_backend.dto.UserResponseDto;
import com.semillero.asistencias_backend.models.RoleEntity;
import com.semillero.asistencias_backend.models.UserEntity;
@Component
public class UserMapper {

    public UserResponseDto toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return UserResponseDto.builder()
                .idUsuario(entity.getIdUsuario())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .nombreCompleto(entity.getNombreCompleto())
                .estado(entity.getEstado())
                .nombreRol(entity.getRole() != null ? entity.getRole().getNombre() : "SIN ROL")
                .build();
    }

    public UserEntity toEntity(UserRequestDto request, RoleEntity role) {
        return UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .nombreCompleto(request.getNombreCompleto())
                .estado(request.getEstado() != null ? request.getEstado() : Integer.valueOf(1))
                .role(role)
                .build();
    }

    public void updateEntity(UserEntity entity, UserRequestDto request, RoleEntity role) {
		// Cambiar el username
        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            entity.setUsername(request.getUsername());
        }
        
        // cambiar estado 
        if (request.getEstado() != null) {
            entity.setEstado(request.getEstado());
        }

        entity.setEmail(request.getEmail());
        entity.setNombreCompleto(request.getNombreCompleto());
        entity.setRole(role);
    }

    public List<UserResponseDto> toDtoList(List<UserEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
