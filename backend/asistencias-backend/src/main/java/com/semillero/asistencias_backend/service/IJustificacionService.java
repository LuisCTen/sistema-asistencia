package com.semillero.asistencias_backend.service;

import com.semillero.asistencias_backend.dto.JustificacionRequestDto;
import com.semillero.asistencias_backend.dto.JustificacionResponseDto;

public interface IJustificacionService {
    JustificacionResponseDto registrarJustificacion(String username, JustificacionRequestDto request);
}
