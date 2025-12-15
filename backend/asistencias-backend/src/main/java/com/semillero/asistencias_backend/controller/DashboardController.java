package com.semillero.asistencias_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.semillero.asistencias_backend.dto.DashboardDto;
import com.semillero.asistencias_backend.service.IDashboardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
private final IDashboardService dashboardService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Solo Admin ve estadísticas
    @GetMapping("/stats")
    public ResponseEntity<DashboardDto> getStats() {
        return ResponseEntity.ok(dashboardService.obtenerMetricas());
    }
}
