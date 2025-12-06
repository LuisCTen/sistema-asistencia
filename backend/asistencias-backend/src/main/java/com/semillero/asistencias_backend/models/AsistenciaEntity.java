package com.semillero.asistencias_backend.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name="ASISTENCIA")
public class AsistenciaEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID_ASISTENCIA")
    private Long idAsistencia;

    //Una asistencia pertenece a un usuario -- FK
    //Si no usamos el Lazy se pondra eager, mejor usar carga perezosa.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_USUARIO", nullable=false)
    private UserEntity user;

    @Column(name="FEC_ASISTENCIA", nullable=false)
    private LocalDate fecAsistencia; // Mapea al DATE de Oracle (solo fecha para el índice)

    @Column(name="HORA_ENTRADA", nullable=false)
    private LocalDateTime horaEntrada; // Mapea al TIMESTAMP

    @Column(name="HORA_SALIDA")
    private LocalDateTime horaSalida;

    @Column(name ="ESTADO_ASISTENCIA",length=20)
    private String estadoAsistencia; // PUNTUAL, TARDE, FALTA
}
