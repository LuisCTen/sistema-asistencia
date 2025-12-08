package com.semillero.asistencias_backend.service;

import com.semillero.asistencias_backend.dto.AsistenciaResponseDto;

public interface IAsistenciaService {
    //Metodos para llamar a PLSQL
    //Hubo un cambio ahora recibe un username en vez del ID
    AsistenciaResponseDto registrarEntrada(String username);
    AsistenciaResponseDto registrarSalida (String username);
}
