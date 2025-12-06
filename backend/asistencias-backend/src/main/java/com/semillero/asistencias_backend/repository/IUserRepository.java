package com.semillero.asistencias_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.semillero.asistencias_backend.models.UserEntity;



public interface IUserRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity>  findByUsername(String username);
}
