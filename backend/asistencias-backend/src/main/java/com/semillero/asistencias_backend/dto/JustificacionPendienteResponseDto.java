package com.semillero.asistencias_backend.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JustificacionPendienteResponseDto {

    private Long idJustificacion;
    private String nombreEmpleado;//username
    private Date fechaIncidencia;
    private String motivo;
    private Date fechaSolicitud;

}
