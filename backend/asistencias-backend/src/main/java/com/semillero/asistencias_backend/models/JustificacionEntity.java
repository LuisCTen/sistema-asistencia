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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="JUSTIFICACION")
public class JustificacionEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID_JUSTIFICACION")
    private Long idJustificacion;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_USUARIO",nullable=false)
    private UserEntity user;

    @Column(name="FEC_INCIDENCIA", nullable=false)
    private LocalDate fechaIncidencia;

    @Lob
    @Column(name="MOTIVO", nullable=false)
    private String motivo;

    @Column(name="ESTADO_SOLICITUD", nullable=false,length=20)
    private String estadoSolicitud;

    @Column(name="FEC_SOLICITUD")
    private LocalDateTime fechaSolicitud;

    @PrePersist
    protected void onCreate(){
        this.fechaSolicitud=LocalDateTime.now();
        if(this.estadoSolicitud==null){
            this.estadoSolicitud="PENDIENTE";
        }
    }
}
