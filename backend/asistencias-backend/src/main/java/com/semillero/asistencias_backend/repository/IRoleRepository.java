package com.semillero.asistencias_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.semillero.asistencias_backend.models.RoleEntity;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByNombre(String nombre);
}
