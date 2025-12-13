CREATE OR REPLACE PACKAGE pkg_justificaciones AS 
   PROCEDURE sp_registrar_justificacion(p_id_usuario       IN  justificacion.id_usuario       %TYPE ,
                                        p_fec_incidencia   IN  justificacion.fec_incidencia %TYPE ,
                                        p_motivo           IN  justificacion.motivo           %TYPE ,
                                        p_resultado       OUT  VARCHAR2                             );
END  pkg_justificaciones;                               
/
CREATE OR REPLACE PACKAGE BODY pkg_justificaciones AS
   --
   PROCEDURE sp_registrar_justificacion(p_id_usuario       IN  justificacion.id_usuario       %TYPE ,
                                        p_fec_incidencia   IN  justificacion.fec_incidencia %TYPE ,
                                        p_motivo           IN  justificacion.motivo           %TYPE ,
                                        p_resultado       OUT  VARCHAR2                             )
   IS
   v_existe NUMBER;
   BEGIN
      --Validar duplicados(si ya solicito una justificacion)
      SELECT COUNT(1)
      INTO   v_existe
      FROM   justificacion a
      WHERE  a.id_usuario  = p_id_usuario
         AND TRUNC(a.fec_incidencia = p_fec_incidencia)
         AND estado_solicitud != 'RECHAZADO';--si fue rechazada, podría enviar nuevamente otra.
      
      IF v_existe > 0 THEN         
         --
         p_resultado :='ERROR: Ya existe una solicitud pendiente o aprobada para esta fecha';
         --
      END IF;
      --Si no existe, entonces la insertamos
      INSERT INTO justificacion (id_usuario,fec_incidencia,motivo)
      VALUES (p_id_usuario,p_fec_incidencia,p_motivo);
      
      COMMIT;
      --
      p_resultado :='OK. Solicitud registrada correctamente';
      --
   EXCEPTION WHEN OTHERS THEN
      ROLLBACK;
      p_resultado :='Error'||SQLERRM;
   END sp_registrar_justificacion;
   --
END pkg_justificaciones;