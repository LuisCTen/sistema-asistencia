package com.semillero.asistencias_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserResponseDto {

    private Long idUsuario;
    private String username;
    private String email;
    private String nombreCompleto;
    private String nombreRol;
    private Integer estado;
}
