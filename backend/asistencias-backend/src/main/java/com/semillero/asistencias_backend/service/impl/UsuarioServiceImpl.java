package com.semillero.asistencias_backend.service.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.semillero.asistencias_backend.service.IUsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final JdbcTemplate jdbcTemplate ;
    @Override
    public Long buscarIdPorUsername(String username) {
        String sql="SELECT id_usuario FROM usuario WHERE username=?";
        try{
            return jdbcTemplate.queryForObject(sql, Long.class,username);

        }catch(Exception e ){
            throw new RuntimeException("Usuario no encontrado "+username);
        }
    }

}
