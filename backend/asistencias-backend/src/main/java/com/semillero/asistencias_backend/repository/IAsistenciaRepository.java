package com.semillero.asistencias_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.semillero.asistencias_backend.models.AsistenciaEntity;

public interface IAsistenciaRepository extends JpaRepository<AsistenciaEntity, Long> {
 //Si son varias asistencias, se podría buscar por fecha.
}
