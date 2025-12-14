package com.semillero.asistencias_backend.service;

import java.util.List;

import com.semillero.asistencias_backend.dto.IncidenciaResponseDto;
import com.semillero.asistencias_backend.dto.JustificacionAtenderRequestDto;
import com.semillero.asistencias_backend.dto.JustificacionPendienteResponseDto;
import com.semillero.asistencias_backend.dto.JustificacionRequestDto;
import com.semillero.asistencias_backend.dto.JustificacionResponseDto;

public interface IJustificacionService {
    //Metodos para empleados
    JustificacionResponseDto registrarJustificacion(String username, JustificacionRequestDto request);



    //Metodos para admin
    List<JustificacionPendienteResponseDto> listarPendientes();

    JustificacionResponseDto atenderSolicitud(Long idJustificacion,JustificacionAtenderRequestDto request);

    //---------------------------------
    public List<IncidenciaResponseDto> listarIncidencias(String username);
}
