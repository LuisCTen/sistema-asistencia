package com.semillero.asistencias_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank (message="El username es requerido")
    private String username;

    @NotBlank (message="La contraseña es requerida")
    private String password;
}
