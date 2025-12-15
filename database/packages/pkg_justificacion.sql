create or replace PACKAGE pkg_justificaciones AS 
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
  --Listar incidencias (Tardanzas + Faltas)
   PROCEDURE sp_listar_incidencias(p_id_usuario IN  justificacion.id_usuario       %TYPE,
                                   p_cursor     OUT SYS_REFCURSOR                       );
END  pkg_justificaciones;                             
/
create or replace PACKAGE BODY pkg_justificaciones AS

   --  REGISTRAR JUSTIFICACIÓN
   PROCEDURE sp_registrar_justificacion(
       p_id_usuario      IN  justificacion.id_usuario%TYPE,
       p_fec_incidencia  IN  justificacion.fec_incidencia%TYPE,
       p_motivo          IN  justificacion.motivo%TYPE,
       p_resultado       OUT VARCHAR2
   ) IS
       v_existe NUMBER;
       v_estado_asistencia VARCHAR2(50); 
   BEGIN

      -- VALIDACIÓN DE CONSISTENCIA
      BEGIN
          -- Buscamos si marcó asistencia ese día
          SELECT estado_asistencia INTO v_estado_asistencia
          FROM asistencia  
          WHERE id_usuario = p_id_usuario
            AND TRUNC(fec_asistencia) = TRUNC(p_fec_incidencia); 

          -- Si encontramos marca, verificamos el estado
          IF v_estado_asistencia = 'PUNTUAL' THEN
             p_resultado := 'ERROR: Usted asistió PUNTUAL ese día. No requiere justificación.';
             RETURN; 
          END IF;

      EXCEPTION
          WHEN NO_DATA_FOUND THEN
             -- Si NO hay datos, FALTO. Se permite justificar.
             NULL; 
      END;

      -- VALIDACIÓN DE DUPLICADOS
      SELECT COUNT(1) INTO v_existe
      FROM justificacion a
      WHERE a.id_usuario = p_id_usuario
        AND TRUNC(a.fec_incidencia) = TRUNC(p_fec_incidencia)
        AND estado_solicitud != 'RECHAZADO';

      IF v_existe > 0 THEN          
         p_resultado :='ERROR: Ya existe una solicitud pendiente o aprobada para esta fecha';
         RETURN;
      END IF;

      -- INSERTAR
      INSERT INTO justificacion (id_usuario, fec_incidencia, motivo)
      VALUES (p_id_usuario, p_fec_incidencia, p_motivo);

      COMMIT;
      p_resultado :='OK. Solicitud registrada correctamente';

   EXCEPTION WHEN OTHERS THEN
      ROLLBACK;
      p_resultado :='Error: '||SQLERRM;
   END sp_registrar_justificacion;

   -- LISTAR PENDIENTES
   PROCEDURE sp_listar_pendientes(p_cursor OUT SYS_REFCURSOR)
   IS
   BEGIN 
      OPEN p_cursor FOR 
         SELECT 
            a.id_justificacion,
            b.username AS nombre_usuario,
            a.fec_incidencia,
            a.motivo,
            a.fec_solicitud
         FROM justificacion a
         INNER JOIN usuario b ON a.id_usuario = b.id_usuario
         WHERE a.estado_solicitud = 'PENDIENTE'
         ORDER BY a.fec_solicitud ASC;
   END sp_listar_pendientes;

   -- ATENDER SOLICITUD
   PROCEDURE sp_atender_solicitud(
       p_id_justificacion IN justificacion.id_justificacion%TYPE, 
       p_estado_nuevo     IN justificacion.estado_solicitud%TYPE,
       p_resultado        OUT VARCHAR2
   ) IS 
       v_estado_actual VARCHAR2(20); 
   BEGIN 
       -- Validar entrada
       IF p_estado_nuevo NOT IN ('APROBADO','RECHAZADO') THEN
           p_resultado := 'ERROR: Estado Invalido. Use APROBADO o RECHAZADO';
           RETURN;
       END IF;

       -- Verificar estado actual 
       BEGIN
           SELECT estado_solicitud INTO v_estado_actual
           FROM justificacion WHERE id_justificacion = p_id_justificacion;
       EXCEPTION WHEN NO_DATA_FOUND THEN
           p_resultado := 'ERROR: No se encontró la justificación';
           RETURN;
       END;

       -- Validar si ya estaba atendida
       IF v_estado_actual = p_estado_nuevo THEN
           p_resultado := 'INFO: La solicitud ya se encontraba en estado ' || p_estado_nuevo;
           RETURN;
       END IF;

       -- Actualizar
       UPDATE justificacion
       SET estado_solicitud = p_estado_nuevo
       WHERE id_justificacion = p_id_justificacion; 

       COMMIT;
       p_resultado := 'OK. Solicitud actualizada a ' || p_estado_nuevo;

   EXCEPTION WHEN OTHERS THEN
       ROLLBACK;
       p_resultado := 'Error: ' || SQLERRM;
   END sp_atender_solicitud;

   PROCEDURE sp_listar_incidencias(
       p_id_usuario IN justificacion.id_usuario%TYPE,
       p_cursor     OUT SYS_REFCURSOR
   ) IS
   BEGIN
      OPEN p_cursor FOR
          -- TARDANZAS 
          SELECT 
              fec_asistencia AS fecha, 
              'TARDANZA' AS tipo,
              'Registrada en sistema' AS detalle
          FROM asistencia
          WHERE id_usuario = p_id_usuario
            AND estado_asistencia = 'TARDE'
            -- Se excluyen las que tienen justificación
            AND NOT EXISTS (
                SELECT 1 FROM justificacion j 
                WHERE j.id_usuario = p_id_usuario 
                  AND TRUNC(j.fec_incidencia) = TRUNC(asistencia.fec_asistencia)
                  AND j.estado_solicitud != 'RECHAZADO'
            )

          UNION ALL
          -- FALTAS 
          SELECT 
              dia_generado AS fecha,
              'FALTA' AS tipo,
              'No marcó asistencia' AS detalle
          FROM (
              SELECT TRUNC(SYSDATE) - LEVEL AS dia_generado
              FROM DUAL
              CONNECT BY LEVEL <= 30
          ) calendario
          WHERE 
            -- Excluir Sábados y Domingos 
            TO_CHAR(dia_generado, 'DY', 'NLS_DATE_LANGUAGE=ENGLISH') NOT IN ('SAT', 'SUN')
            -- Quitar días que sí tienen asistencia
            AND NOT EXISTS (
                SELECT 1 FROM asistencia a 
                WHERE a.id_usuario = p_id_usuario 
                  AND TRUNC(a.fec_asistencia) = calendario.dia_generado
            )
            -- Excluir días ya justificados
            AND NOT EXISTS (
                SELECT 1 FROM justificacion j
                WHERE j.id_usuario = p_id_usuario 
                  AND TRUNC(j.fec_incidencia) = calendario.dia_generado
                  AND j.estado_solicitud != 'RECHAZADO'
            )
          ORDER BY fecha DESC;
   END sp_listar_incidencias;

END pkg_justificaciones;