package com.semillero.asistencias_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JustificacionAtenderRequestDto {
    //El id viaja por URL
    @NotNull
    private String estado; //Aprobado o Rechazado.
}
