package com.semillero.asistencias_backend.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistorialResponseDto {

    private Long idAsistencia;
    private Date fecAsistencia;
    private Date horaEntrada;
    private Date horaSalida;
    private String estadoAsistencia;

    //
    @JsonIgnore
    private Long totalRegistros;

}
