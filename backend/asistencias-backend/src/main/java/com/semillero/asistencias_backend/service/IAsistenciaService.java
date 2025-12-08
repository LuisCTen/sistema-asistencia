package com.semillero.asistencias_backend.service;

import com.semillero.asistencias_backend.dto.AsistenciaResponseDto;

public interface IAsistenciaService {
    //Metodos para llamar a PLSQL
    AsistenciaResponseDto registrarEntrada(Long idUsuario);
    AsistenciaResponseDto registrarSalida (Long idUsuario);
}
