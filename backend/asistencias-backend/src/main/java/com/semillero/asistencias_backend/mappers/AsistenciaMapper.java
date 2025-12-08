package com.semillero.asistencias_backend.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.semillero.asistencias_backend.dto.AsistenciaRequestDto;
import com.semillero.asistencias_backend.dto.AsistenciaResponseDto;
import com.semillero.asistencias_backend.models.AsistenciaEntity;
import com.semillero.asistencias_backend.models.UserEntity;

@Component
public class AsistenciaMapper {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;//YYYY-MM-DD
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    //Que debe responder el Stored Procedure
    public AsistenciaResponseDto toDtoFromSp(String mensajeOracle,Long idUsuario){
        LocalDateTime now = LocalDateTime.now();
        return AsistenciaResponseDto.builder()
                           .mensaje(mensajeOracle)
                           .idUsuario(idUsuario)
                           .fecAsistencia(now.format(dateFormatter))//"2025-12-08"
                           .horaEntrada(now.format(timeFormatter))
                           .build();
    }
    //Asistencia Entity a Response DTO(CRUD)
    public AsistenciaResponseDto tDto(AsistenciaEntity asistenciaEntity){
        return AsistenciaResponseDto.builder()
                            .idAsistencia(asistenciaEntity.getIdAsistencia())
                            .idUsuario(asistenciaEntity.getUser().getIdUsuario())
                            .nombreUsuario(asistenciaEntity.getUser().getNombreCompleto())
                            .fecAsistencia(asistenciaEntity.getFecAsistencia().toString())
                            .horaEntrada(asistenciaEntity.getHoraEntrada() !=null ? asistenciaEntity.getHoraEntrada().format(timeFormatter):null)
                            .horaSalida(asistenciaEntity.getHoraSalida()!=null ? asistenciaEntity.getHoraEntrada().format(timeFormatter):null)
                            .build();
    }

    //Ahora del request al entity 
    public AsistenciaEntity toEntity (AsistenciaRequestDto asistenciaRequestDto){
        return AsistenciaEntity.builder()
                        .user(UserEntity.builder().idUsuario(asistenciaRequestDto.getIdUsuario()).build())
                        .fecAsistencia(asistenciaRequestDto.getFecAsistencia())
                        .horaEntrada(asistenciaRequestDto.getHoraEntrada())
                        .horaSalida(asistenciaRequestDto.getHoraSalida())
                        .estadoAsistencia(asistenciaRequestDto.getEstadoAsistencia())
                        .build();
    }

}
