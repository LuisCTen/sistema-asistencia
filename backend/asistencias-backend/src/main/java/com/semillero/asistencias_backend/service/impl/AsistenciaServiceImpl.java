package com.semillero.asistencias_backend.service.impl;

import org.springframework.stereotype.Service;

import com.semillero.asistencias_backend.dto.AsistenciaResponseDto;
import com.semillero.asistencias_backend.mappers.AsistenciaMapper;
import com.semillero.asistencias_backend.repository.IAsistenciaRepository;
import com.semillero.asistencias_backend.service.IAsistenciaService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
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
       try{
            StoredProcedureQuery query =entityManager.createStoredProcedureQuery(nombreSp);
            query.registerStoredProcedureParameter("p_id_usuario", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_resultado", String.class, ParameterMode.OUT);
            query.setParameter("p_id_usuario", idUsuario);
            query.execute();
            return (String) query.getParameterValue("p_resultado");

       } catch(Exception e){
            throw new RuntimeException("Error Oracle:"+e.getMessage());
       }
    }
    //--------------------------------------------------------------------------------------------
    //METODOS CRUD
}

