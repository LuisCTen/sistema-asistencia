package com.semillero.asistencias_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USUARIO")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "USERNAME", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "CLAVE_HASH", nullable = false, length = 100)
    private String password;

    @Column(name = "NOMBRE_COMPLETO", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "ESTADO", nullable = false)
    private Integer estado; // 1=Activo, 0=Inactivo

    // Relación: Muchos usuarios tienen un Rol
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ROL", nullable = false)
    private RoleEntity role;
}