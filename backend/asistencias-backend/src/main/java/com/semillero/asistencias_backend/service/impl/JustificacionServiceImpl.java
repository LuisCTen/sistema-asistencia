package com.semillero.asistencias_backend.service.impl;

import java.sql.Date;
import java.sql.Types;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.semillero.asistencias_backend.dto.JustificacionRequestDto;
import com.semillero.asistencias_backend.dto.JustificacionResponseDto;
import com.semillero.asistencias_backend.service.IJustificacionService;
import com.semillero.asistencias_backend.service.IUsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JustificacionServiceImpl implements IJustificacionService {

    private final JdbcTemplate jdbcTemplate;
    private final IUsuarioService iUsuarioService;
    
    @Override
    public JustificacionResponseDto registrarJustificacion(String username, JustificacionRequestDto request) {
        //Buscar el id del usuario
        Long idUsuario = iUsuarioService.buscarIdPorUsername(username);
        //llamada al SP
        SimpleJdbcCall jdbcCall =new SimpleJdbcCall(jdbcTemplate)
                                .withCatalogName("PKG_JUSTIFICACIONES")
                                .withProcedureName("SP_REGISTRAR_JUSTIFICACION")
                                .declareParameters(
                                    new SqlParameter("p_id_usuario",Types.NUMERIC),
                                    new SqlParameter("p_fec_incidencia", Types.DATE),
                                    new SqlParameter("p_motivo",Types.CLOB),
                                    new SqlOutParameter("p_resultado",Types.VARCHAR)
                                );
        //Ejecutamos SP
        Map<String,Object> result =jdbcCall.execute(Map.of(
            "p_id_usuario",idUsuario,
            "p_fec_incidencia",Date.valueOf(request.getFechaIncidencia()),
            "p_motivo",request.getMotivo()
        ));

        return JustificacionResponseDto.builder()
                            .mensaje((String)result.get("p_resultado"))
                            .build();
    }
    

}
