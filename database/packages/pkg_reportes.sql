/*============================================
PROYECTO     : Sistema de control de asistencias
BD           : Oracle
AUTOR        : Luis Carbajal
DESCRIPCION  : Se procede a crear la especificacion del paquete para reportes
FECHA        : 10/12/2025
=============================================*/
--Especificación
--Nota se usara SYS_REFCURSOR (puntero) para que Java lo lea fila por fila
CREATE OR REPLACE PACKAGE pkg_reportes AS
--
   --
   PROCEDURE sp_listar_historial(p_id_usuario IN usuario.id_usuario        %TYPE,
                                 p_fec_inicio IN asistencia.fec_asistencia %TYPE DEFAULT NULL,--si es null trae desde el inicio
                                 p_fec_fin    IN asistencia.fec_asistencia %TYPE DEFAULT NULL,--trae hasta hoy si es null.
                                 p_cursor    OUT SYS_REFCURSOR
   );
   --
--
END pkg_reportes;
CREATE OR REPLACE PACKAGE BODY pkg_reportes AS
   PROCEDURE sp_listar_historial(p_id_usuario IN usuario.id_usuario        %TYPE,
                                 p_fec_inicio IN asistencia.fec_asistencia %TYPE DEFAULT NULL,
                                 p_fec_fin    IN asistencia.fec_asistencia %TYPE DEFAULT NULL,
                                 p_cursor    OUT SYS_REFCURSOR
   )
IS 
   BEGIN
      OPEN p_cursor FOR
         SELECT 
            a.id_asistencia,
            a.fec_asistencia,
            a.hora_entrada,
            a.hora_salida,
            a.estado_asistencia
         FROM   asistencia a
         WHERE  a.id_usuario = p_id_usuario
            AND (p_fec_inicio IS NULL OR a.fec_asistencia>=p_fec_inicio)
            AND (p_fec_fin    IS NULL OR a.fec_asistencia>=p_fec_fin   ) 
         ORDER BY a.fec_asistencia DESC,a.hora_entrada DESC;
    EXCEPTION
       WHEN OTHERS THEN
          OPEN p_cursor FOR SELECT NULL FROM dual WHERE 1=0;--sería mejor guardarlo en un tabla de log
       END sp_listar_historial;
END pkg_reportes;