package com.semillero.asistencias_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaRequestDto {

    private Long idUsuario;

    private LocalDate fecAsistencia;

    private LocalDateTime horaEntrada;

    private LocalDateTime horaSalida;

    private String estadoAsistencia; // PUNTUAL, TARDE, FALTA
}
