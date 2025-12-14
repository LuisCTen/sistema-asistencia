package com.semillero.asistencias_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.semillero.asistencias_backend.dto.UserResponseDto;
import com.semillero.asistencias_backend.models.UserEntity;



public interface IUserRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity>  findByUsername(String username);
    //Validar la unicidad del nombre de usuario
    boolean existsByUsername(String username);
    //Validar la unicidad del correo electrónico
    boolean existsByEmail(String email);

    @Query("SELECT new com.semillero.asistencias_backend.dto.UserResponseDto(" + 
            "u.idUsuario, u.username, u.email, u.nombreCompleto, r.nombre, u.estado) " +
            "FROM UserEntity u JOIN u.role r " +
            "ORDER BY u.idUsuario DESC")
    List<UserResponseDto> findAllToDto();

}
