package com.semillero.asistencias_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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


    @PostMapping("/marcar-asistencia/{username}")
    public ResponseEntity<AsistenciaResponseDto> marcarAsistencia(@PathVariable String username){
        
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(iAsistenciaService.registrarEntrada(username));
    }

}
