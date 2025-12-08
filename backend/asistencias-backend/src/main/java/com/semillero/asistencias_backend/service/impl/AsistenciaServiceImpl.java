package com.semillero.asistencias_backend.service.impl;

import org.springframework.stereotype.Service;

import com.semillero.asistencias_backend.dto.AsistenciaResponseDto;
import com.semillero.asistencias_backend.mappers.AsistenciaMapper;
import com.semillero.asistencias_backend.repository.IAsistenciaRepository;
import com.semillero.asistencias_backend.service.IAsistenciaService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements IAsistenciaService{

    private final EntityManager entityManager;
    private final IAsistenciaRepository iAsistenciaRepository;
    private final AsistenciaMapper asistenciaMapper;
    
    //--------------------------------------------------------------------------------------------
    //Metodos que implementan la logica de mi interfaz
    @Override
    @Transactional//Maneja la transaccion a la bd
    public AsistenciaResponseDto registrarEntrada(Long idUsuario) {
        String mensajeOracle = ejecutarProcedimiento("PKG_ASISTENCIAS.SP_REGISTRAR_ENTRADA", idUsuario);
        //Nos ayudamos del mapper para estructurar respuesta
        return asistenciaMapper.toDtoFromSp(mensajeOracle, idUsuario) ;
    }

    @Override
    public AsistenciaResponseDto registrarSalida(Long idUsuario) {
        String mensajeOracle =ejecutarProcedimiento("PKG_ASISTENCIAS.SP_REGISTRAR_SALIDA", idUsuario);
        return asistenciaMapper.toDtoFromSp(mensajeOracle, idUsuario);
    }
    //--------------------------------------------------------------------------------------------
    //Este metodo llama al Store Procedure

    private String ejecutarProcedimiento(String nombreSp, Long idUsuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

