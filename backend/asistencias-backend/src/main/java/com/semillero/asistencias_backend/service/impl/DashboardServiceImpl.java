package com.semillero.asistencias_backend.service.impl;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import com.semillero.asistencias_backend.dto.DashboardDto;
import com.semillero.asistencias_backend.repository.*;
import com.semillero.asistencias_backend.service.IDashboardService;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements IDashboardService {

    private final IUserRepository userRepository;
    private final IAsistenciaRepository asistenciaRepository;
    private final IJustificacionRepository justificacionRepository;

    @Override
    public DashboardDto obtenerMetricas() {
        LocalDate hoy = LocalDate.now();

        return DashboardDto.builder()
                .totalEmpleados(userRepository.countByEstado(1))
                .asistenciasHoy(asistenciaRepository.countByFecAsistencia(hoy))
                .tardanzasHoy(asistenciaRepository.countByFecAsistenciaAndEstadoAsistencia(hoy, "TARDE"))
                .justificacionesPendientes(justificacionRepository.countByEstadoSolicitud("PENDIENTE"))
                .build();
    }
}
