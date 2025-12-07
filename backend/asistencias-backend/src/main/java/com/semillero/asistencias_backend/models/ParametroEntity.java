package com.semillero.asistencias_backend.models;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PARAMETRO")
public class ParametroEntity {

    @Id
    @Column(name="CLAVE", length=50)
    private String clave;

    @Column(name="VALOR", nullable=false,length=100)
    private String valor;

    @Column(name="DESCRIPCION", length=200)
    private String descripcion;
}
