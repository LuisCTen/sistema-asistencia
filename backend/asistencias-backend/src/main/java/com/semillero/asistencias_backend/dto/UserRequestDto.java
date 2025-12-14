package com.semillero.asistencias_backend.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String password;//Debe ser solo obligatorio al crear
    private String email;
    private String nombreCompleto;
    private Long idRol;//Recibimos solo el id del Rol
    private Integer estado;//1=Activo 0=Inactivo
}
