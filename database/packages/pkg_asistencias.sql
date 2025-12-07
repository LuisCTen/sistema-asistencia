/*============================================
PROYECTO     : Sistema de control de asistencias
BD           : Oracle
AUTOR        : Luis Carbajal
DESCRIPCION  : Se procede a crear la especificacion del paquete para registro de asistencias
FECHA        : 07/12/2025
=============================================*/
--Especificación
CREATE OR REPLACE PACKAGE pkg_asistencias 
AS
   --
   --Se debe registrar entrada y solo devolver si se hizo el registro o no.
   PROCEDURE sp_registrar_entrada(p_id_usuario  IN bd_asistencias.id_usuario %TYPE ,
                                  p_resultado  OUT VARCHAR2                        );
   --Se debe registrar salida y solo devolver si se registro o no.
   PROCEDURE sp_registrar_salida( p_id_usuario  IN bd_asistencias.id_usuario %TYPE ,
                                  p_resultado  OUT VARCHAR2                        );
end pkg_asistencias;
/