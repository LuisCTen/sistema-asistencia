package com.semillero.asistencias_backend.service;

import java.util.List;

import com.semillero.asistencias_backend.commons.ICrudCommonsDto;
import com.semillero.asistencias_backend.dto.UserRequestDto;
import com.semillero.asistencias_backend.dto.UserResponseDto;

public interface IUsuarioService extends ICrudCommonsDto<UserRequestDto, UserResponseDto, Long> {
    //Metodo para Auth y Asistencia

    Long buscarIdPorUsername(String username);

    List<UserResponseDto> listarTodos();
}
