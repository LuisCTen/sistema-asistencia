package com.semillero.asistencias_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaResponseDto {

   //Mensaje del SP(Si se registro entrada o salida)
    private String mensaje;

    private Long idAsistencia;
    private Long idUsuario;
    private String nombreUsuario;

    private String fecAsistencia;
    private String horaEntrada;
    private String horaSalida;
    private String estadoAsistencia;
}
