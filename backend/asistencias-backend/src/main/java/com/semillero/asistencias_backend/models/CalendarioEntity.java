package com.semillero.asistencias_backend.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="CALENDARIO_LABORAL")
public class CalendarioEntity {

    @Id
    @Column(name="FEC_CALENDARIO",nullable=false)
    private LocalDate fecha_calendario;

    @Column(name="TIPO",nullable=false,length=20)
    private String tipo;// FERIADO, DIA_LIBRE

    @Column(name="DESCRIPCION",length=100)
    private String descripcion;
    
}
