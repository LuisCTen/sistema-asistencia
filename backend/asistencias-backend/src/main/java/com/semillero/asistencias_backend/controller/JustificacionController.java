package com.semillero.asistencias_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.semillero.asistencias_backend.dto.IncidenciaResponseDto;
import com.semillero.asistencias_backend.dto.JustificacionAtenderRequestDto;
import com.semillero.asistencias_backend.dto.JustificacionRequestDto;
import com.semillero.asistencias_backend.dto.JustificacionResponseDto;
import com.semillero.asistencias_backend.service.IJustificacionService;

import org.springframework.web.bind.annotation.RequestBody;

import com.semillero.asistencias_backend.dto.JustificacionPendienteResponseDto;

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

    // LISTAR PENDIENTES 
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pendientes")
    public ResponseEntity<List<JustificacionPendienteResponseDto>> listarPendientes() {
        return ResponseEntity.ok(justificacionService.listarPendientes());
    }
    // ATENDER SOLICITUD 
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/atender") 
    public ResponseEntity<JustificacionResponseDto> atenderSolicitud(
            @PathVariable("id") Long idJustificacion,
            @RequestBody JustificacionAtenderRequestDto requestDto) {
        
        // Validación 
        if (requestDto.getEstado() == null || requestDto.getEstado().trim().isEmpty()) {
             return ResponseEntity.badRequest().body(
                 JustificacionResponseDto.builder().mensaje("ERROR: El estado es obligatorio").build()
             );
        }

        JustificacionResponseDto response = justificacionService.atenderSolicitud(idJustificacion, requestDto);

        if (response.getMensaje().startsWith("ERROR")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('ROLE_EMPLEADO') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/incidencias")
    public ResponseEntity<List<IncidenciaResponseDto>> getIncidencias() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(justificacionService.listarIncidencias(username));
    }
}
