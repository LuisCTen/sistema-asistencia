package com.semillero.asistencias_backend.service.impl;

import org.springframework.stereotype.Service;

import com.semillero.asistencias_backend.dto.AsistenciaResponseDto;
import com.semillero.asistencias_backend.service.IAsistenciaService;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements IAsistenciaService{

    private final EntityManager entityManager;

    @Override
    public AsistenciaResponseDto registrarEntrada(Long idUsuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AsistenciaResponseDto registrarSalida(Long idUsuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
