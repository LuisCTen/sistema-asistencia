CREATE OR REPLACE PACKAGE pkg_justificaciones AS 
   PROCEDURE sp_registrar_justificacion(p_id_usuario       IN  justificacion.id_usuario       %TYPE ,
                                        p_fec_incidencia   IN  justificacion.fec_incidencia %TYPE ,
                                        p_motivo           IN  justificacion.motivo           %TYPE ,
                                        p_resultado       OUT  VARCHAR2                             );
   --Lista todas las justificaciones pendientes (ADMIN)
   PROCEDURE sp_listar_pendientes(p_cursor OUT SYS_REFCURSOR);
   
   --Aprobar o rechazar justificacion pendiente
   PROCEDURE sp_atender_solicitud(p_id_justificacion  IN justificacion.id_justificacion %TYPE   ,
                                   p_estado_nuevo     IN justificacion. estado_solicitud %TYPE  ,
                                   p_resultado       OUT VARCHAR2                               );
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
		 RETURN;
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
   --
   PROCEDURE sp_listar_pendientes(p_cursor OUT SYS_REFCURSOR)
   IS
   BEGIN 
      --
      OPEN p_cursor FOR 
         --
         SELECT 
            a.id_justificacion,
            b.username as nombre_usuario,
            a.fec_incidencia,
            a.motivo,
            a.fec_solicitud
         FROM justificacion a
         INNER JOIN usuario b
         ON    a.id_usuario  = b.id_usuario
         WHERE a.estado_solicitud = 'PENDIENTE'
         ORDER BY a.fec_solicitud ASC; --Para poner adelante los mas antiguos
         --
      --
   END sp_listar_pendientes;
   --Aprobar o rechazar solicitud
   PROCEDURE sp_atender_solicitud(
    p_id_justificacion IN justificacion.id_justificacion %TYPE   ,
    p_estado_nuevo     IN justificacion. estado_solicitud %TYPE  ,
    p_resultado       OUT VARCHAR2                               )
   IS 
   BEGIN 
      --
      IF p_estado_nuevo NOT IN ('APROBADO','RECHAZADO') THEN
         --
         p_resultado := 'ERROR: Estado Invalido.Use aprobado o rechazado';
         RETURN;
         --
      END IF;
      --
      UPDATE justificacion
      SET estado_solicitud =p_estado_nuevo
      WHERE id_justificacion = p_id_justificacion;
      --
      IF SQL%ROWCOUNT = 0 THEN
         --
         p_resultado :='ERROR: No se encontró la justificación';
         --
      ELSE
         --
         COMMIT;
         p_resultado := 'OK. Solicitud actualizada a '||p_estado_nuevo;
         --
      END IF;
   --
   EXCEPTION WHEN OTHERS THEN
      ROLLBACK;
      p_resultado :='Error'||SQLERRM;
   END sp_atender_solicitud;
   --
END pkg_justificaciones;