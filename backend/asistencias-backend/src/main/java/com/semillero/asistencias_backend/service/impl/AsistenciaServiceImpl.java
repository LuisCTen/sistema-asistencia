package com.semillero.asistencias_backend.service.impl;

import java.sql.Types;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.semillero.asistencias_backend.dto.AsistenciaResponseDto;
import com.semillero.asistencias_backend.exception.BadRequestException;
import com.semillero.asistencias_backend.exception.ResourceNotFoundException;
import com.semillero.asistencias_backend.mappers.AsistenciaMapper;
import com.semillero.asistencias_backend.models.UserEntity;
import com.semillero.asistencias_backend.repository.IAsistenciaRepository;
import com.semillero.asistencias_backend.repository.IUserRepository;
import com.semillero.asistencias_backend.service.IAsistenciaService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements IAsistenciaService {

    private final EntityManager entityManager;
    private final IAsistenciaRepository iAsistenciaRepository;
    private final IUserRepository iUserRepository;
    private final AsistenciaMapper asistenciaMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //--------------------------------------------------------------------------------------------
    //Metodo para buscarIdPorNombre
    private Long buscarIdPorUsername(String username) {

        UserEntity userEntity = iUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return userEntity.getIdUsuario();
    }

    //--------------------------------------------------------------------------------------------
    //Metodos que implementan la logica de mi interfaz
    @Override
    @Transactional//Maneja la transaccion a la bd
    public AsistenciaResponseDto registrarEntrada(String username) {

        //Buscar el id del usuario
        Long idUsuario = buscarIdPorUsername(username);

        //Llamar al SP
        String mensajeOracle = ejecutarProcedimiento("SP_REGISTRAR_ENTRADA", idUsuario);

        	if (mensajeOracle.startsWith("ERROR")) {

                throw new BadRequestException(mensajeOracle); 
	        }
        //Nos ayudamos del mapper para estructurar respuesta
        return asistenciaMapper.toDtoFromSp(mensajeOracle, idUsuario);
    }

    @Override
    public AsistenciaResponseDto registrarSalida(String username) {
        //LLama al metodo y busca id por usuario
        Long idUsuario = buscarIdPorUsername(username);

        //Lama al SP
        String mensajeOracle = ejecutarProcedimiento("SP_REGISTRAR_SALIDA", idUsuario);
                	if (mensajeOracle.startsWith("ERROR")) {

                throw new BadRequestException(mensajeOracle); 
	        }
        return asistenciaMapper.toDtoFromSp(mensajeOracle, idUsuario);
    }
    //--------------------------------------------------------------------------------------------
    //Este metodo llama al Store Procedure

    private String ejecutarProcedimiento(String nombreSp, Long idUsuario) {

        try {
            SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                    .withSchemaName("ASISTENCIA_SCHEMA") 
                    .withCatalogName("PKG_ASISTENCIAS") // nombre del paquete
                    .withProcedureName(nombreSp) // nombre del SP sin paquete
                    .declareParameters(
                            new SqlParameter("p_id_usuario", Types.NUMERIC),
                            new SqlOutParameter("p_resultado", Types.VARCHAR)
                    );

            Map<String, Object> result = call.execute(idUsuario);

            return (String) result.get("p_resultado");

        } catch (Exception e) {
            throw new RuntimeException("Error Oracle: " + e.getMessage(), e);
        }
    }
    /*       try{
            StoredProcedureQuery query =entityManager.createStoredProcedureQuery(nombreSp);
            query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(2, Object.class, ParameterMode.OUT);
            query.setParameter(1, idUsuario);

            query.execute();

            return (String) query.getParameterValue(2);

       } catch(Exception e){
            System.err.println(" Error en SP: " + e.getMessage());
            throw new RuntimeException("Error Oracle:"+e.getMessage());
       }
    } */
    //--------------------------------------------------------------------------------------------
    //METODOS CRUD
}
