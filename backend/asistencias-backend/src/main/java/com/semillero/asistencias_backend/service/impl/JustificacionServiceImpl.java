package com.semillero.asistencias_backend.service.impl;

import java.sql.Date;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.semillero.asistencias_backend.dto.IncidenciaResponseDto;
import com.semillero.asistencias_backend.dto.JustificacionAtenderRequestDto;
import com.semillero.asistencias_backend.dto.JustificacionPendienteResponseDto;
import com.semillero.asistencias_backend.dto.JustificacionRequestDto;
import com.semillero.asistencias_backend.dto.JustificacionResponseDto;
import com.semillero.asistencias_backend.service.IJustificacionService;
import com.semillero.asistencias_backend.service.IUsuarioService;

import jakarta.transaction.Transactional;
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
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_JUSTIFICACIONES")
                .withProcedureName("SP_REGISTRAR_JUSTIFICACION")
                .declareParameters(
                        new SqlParameter("p_id_usuario", Types.NUMERIC),
                        new SqlParameter("p_fec_incidencia", Types.DATE),
                        new SqlParameter("p_motivo", Types.CLOB),
                        new SqlOutParameter("p_resultado", Types.VARCHAR)
                );
        //Ejecutamos SP
        Map<String, Object> result = jdbcCall.execute(Map.of(
                "p_id_usuario", idUsuario,
                "p_fec_incidencia", Date.valueOf(request.getFechaIncidencia()),
                "p_motivo", request.getMotivo()
        ));

        return JustificacionResponseDto.builder()
                .mensaje((String) result.get("p_resultado"))
                .build();
    }
    //listar pendientes (ADMIN)

    @Override
    @Transactional
    public List<JustificacionPendienteResponseDto> listarPendientes() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_JUSTIFICACIONES")
                .withProcedureName("SP_LISTAR_PENDIENTES")
                .returningResultSet("p_cursor", new RowMapper<JustificacionPendienteResponseDto>() {
                    @Override
                    public JustificacionPendienteResponseDto mapRow(java.sql.ResultSet rs, int rownum) throws java.sql.SQLException {
                        //Mapeo del resulset del dto
                        return JustificacionPendienteResponseDto.builder()
                                .idJustificacion(rs.getLong("ID_JUSTIFICACION"))
                                .nombreEmpleado(rs.getString("NOMBRE_USUARIO"))
                                .fechaIncidencia(rs.getDate("FEC_INCIDENCIA"))
                                .motivo(rs.getString("MOTIVO"))
                                .fechaSolicitud(rs.getTimestamp("FEC_SOLICITUD"))
                                .build();
                    }
                });
        //Ejecutamos
        Map<String, Object> result = jdbcCall.execute(new HashMap<>());

        return (List<JustificacionPendienteResponseDto>) result.get("p_cursor");
    }

    //Atender solicitud (ADMIN)
    @Override
    @Transactional
    public JustificacionResponseDto atenderSolicitud(Long idJustificacion, JustificacionAtenderRequestDto request) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_JUSTIFICACIONES")
                .withProcedureName("SP_ATENDER_SOLICITUD")
                .declareParameters(
                        new SqlParameter("p_id_justificacion", Types.NUMERIC),
                        new SqlParameter("p_estado_nuevo", Types.VARCHAR),
                        new SqlOutParameter("p_resultado", Types.VARCHAR)
                );

        Map<String, Object> result = jdbcCall.execute(Map.of(
                "p_id_justificacion", idJustificacion,
                "p_estado_nuevo", request.getEstado()
        ));

        return JustificacionResponseDto.builder()
                .mensaje((String) result.get("p_resultado"))
                .build();
    }


    @Override
    public List<IncidenciaResponseDto> listarIncidencias(String username) {
        Long idUsuario = iUsuarioService.buscarIdPorUsername(username);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_JUSTIFICACIONES")
                .withProcedureName("SP_LISTAR_INCIDENCIAS")
                .declareParameters(new SqlParameter("p_id_usuario", Types.NUMERIC))
                .returningResultSet("p_cursor", (rs, rowNum) -> 
                    IncidenciaResponseDto.builder()
                        .fecha(rs.getDate("fecha"))
                        .tipo(rs.getString("tipo"))
                        .detalle(rs.getString("detalle"))
                        .build()
                );

        Map<String, Object> result = jdbcCall.execute(Map.of("p_id_usuario", idUsuario));
        return (List<IncidenciaResponseDto>) result.get("p_cursor");
    }
}


