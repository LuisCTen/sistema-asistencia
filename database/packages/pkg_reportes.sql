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
   PROCEDURE fn_listar_historial(p_id_usuario IN usuario.id_usuario        %TYPE,
                                 p_fec_inicio IN asistencia.fec_asistencia %TYPE DEFAULT NULL,--si es null trae desde el inicio
                                 p_fec_fin    IN asistencia.fec_asistencia %TYPE DEFAULT NULL,--trae hasta hoy si es null.
                                 p_cursor    OUT SYS_REFCURSOR
   );
   --
--
END pkg_reportes;