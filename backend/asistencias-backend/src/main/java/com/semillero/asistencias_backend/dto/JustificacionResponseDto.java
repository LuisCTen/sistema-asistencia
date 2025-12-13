package com.semillero.asistencias_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JustificacionResponseDto {
    private String mensaje;
    //Aqui se tmabien puede retornar el id de la solicitud(justificacion)
}
