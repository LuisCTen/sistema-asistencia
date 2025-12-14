package com.semillero.asistencias_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaResponseDto {
    private Date fecha;
    private String tipo;    // "TARDANZA" o "FALTA"
    private String detalle; 
}