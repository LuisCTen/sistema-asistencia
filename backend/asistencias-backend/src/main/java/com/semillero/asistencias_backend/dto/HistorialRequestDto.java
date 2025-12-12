package com.semillero.asistencias_backend.dto;
import java.time.LocalDate;

import lombok.Data;

@Data
public class HistorialRequestDto {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
