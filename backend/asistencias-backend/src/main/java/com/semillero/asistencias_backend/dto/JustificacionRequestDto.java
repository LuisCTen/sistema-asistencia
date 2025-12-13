package com.semillero.asistencias_backend.dto;

import java.time.LocalDate;

import lombok.Data;


@Data
public class JustificacionRequestDto {
    private LocalDate fechaIncidencia;//YYYY-MM-DD
    private String motivo;
}


