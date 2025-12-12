package com.semillero.asistencias_backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.semillero.asistencias_backend.dto.AsistenciaResponseDto;
import com.semillero.asistencias_backend.dto.HistorialRequestDto;
import com.semillero.asistencias_backend.dto.HistorialResponseDto;

public interface IAsistenciaService {
    //Metodos para llamar a PLSQL
    //Hubo un cambio ahora recibe un username en vez del ID
    AsistenciaResponseDto registrarEntrada(String username);
    AsistenciaResponseDto registrarSalida (String username);
    Page<HistorialResponseDto> listarHistorial(String username,HistorialRequestDto filtros,Pageable pageable);
}
