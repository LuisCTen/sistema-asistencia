package com.semillero.asistencias_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.semillero.asistencias_backend.dto.AsistenciaResponseDto;
import com.semillero.asistencias_backend.service.IAsistenciaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    private final IAsistenciaService iAsistenciaService;

    @PreAuthorize("hasRole('EMPLEADO') or hasRole('ADMIN')")
    @PostMapping("/marcar-asistencia")
    public ResponseEntity<AsistenciaResponseDto> marcarAsistencia(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(iAsistenciaService.registrarEntrada(username));
    }

    @PreAuthorize("hasRole('EMPLEADO') or hasRole('ADMIN')")
    @PostMapping("/marcar-salida")
    public ResponseEntity<AsistenciaResponseDto> marcarSalida(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.OK)
                    .body(iAsistenciaService.registrarSalida(username));
    }

}
