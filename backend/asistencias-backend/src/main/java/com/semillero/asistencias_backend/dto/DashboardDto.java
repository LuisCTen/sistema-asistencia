package com.semillero.asistencias_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardDto {
    private Long totalEmpleados;
    private Long asistenciasHoy;
    private Long tardanzasHoy;
    private Long justificacionesPendientes;
}