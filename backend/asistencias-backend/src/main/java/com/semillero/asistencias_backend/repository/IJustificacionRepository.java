package com.semillero.asistencias_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.semillero.asistencias_backend.models.JustificacionEntity;

public interface  IJustificacionRepository extends JpaRepository<JustificacionEntity, Long> {
   long countByEstadoSolicitud(String estado);
}
