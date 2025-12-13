package com.semillero.asistencias_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.semillero.asistencias_backend.dto.JustificacionRequestDto;
import com.semillero.asistencias_backend.dto.JustificacionResponseDto;
import com.semillero.asistencias_backend.service.IJustificacionService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/justificaciones")
@RequiredArgsConstructor
public class JustificacionController {
    private final IJustificacionService justificacionService;

    @PreAuthorize("hasRole('EMPLEADO') or hasRole('ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<JustificacionResponseDto> registrarJustificacion(@RequestBody JustificacionRequestDto request){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        JustificacionResponseDto response = justificacionService.registrarJustificacion(username, request);
    
        if(response.getMensaje() !=null && response.getMensaje().startsWith("Error")){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

}
