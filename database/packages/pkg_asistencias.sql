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
--Body
CREATE OR REPLACE PACKAGE BODY pkg_asistencias 
AS
--
   --
   FUNCTION obtener_fecha_asistencia RETURN DATE IS
   BEGIN
   
     return trunc(sysdate); 
   END obtener_fecha_asistencia;
   --
   PROCEDURE sp_registrar_entrada(p_id_usuario  IN usuario.id_usuario %TYPE ,
                                  p_resultado  OUT VARCHAR2                        )
   IS
      --
      l_estado_usuario    usuario.estado %TYPE              ;
      l_existe            NUMBER                            ;
      l_fec_actual        DATE                              ;
      l_hora_base         parametro.valor %TYPE             ;
      l_tolerancia        NUMBER                            ;
      l_hora_actual       TIMESTAMP :=SYSTIMESTAMP          ;
      
      l_limite_entrada    TIMESTAMP                         ;
      l_estado_asistencia asistencia.estado_asistencia %TYPE;
      -- 
   BEGIN
      
      l_fec_actual :=obtener_fecha_asistencia();
      --Validar que el usuario esté activo (regla de negocio)
      BEGIN
         SELECT a.estado 
         INTO   l_estado_usuario
         FROM   usuario a
         WHERE  a.id_usuario = p_id_usuario;
         --
         IF l_estado_usuario = 0 THEN 
            p_resultado :='Error: Usuario inactivo.';
            RETURN;
         END IF;
         --
      EXCEPTION WHEN NO_DATA_FOUND THEN 
         --
         p_resultado := 'Error: Usuario no encontrado';
         RETURN;
         --
      END;
      --Validar que no pueda marcar 2 veces (regla de negocio)
      SELECT COUNT(1)
      INTO l_existe
      FROM asistencia a
      WHERE  a.id_usuario     = p_id_usuario
         AND a.fec_asistencia = l_fec_actual;
      --
      IF l_existe>0 THEN
         --
         p_resultado :='Ya existe una asistencia regitrada para hoy';
         RETURN;
         --
      END IF;
      --
      /*Calculemos el estado de asistencia('Puntual'o Tardanza)*/
      --Obtener de parametros la hora de ingreso y el tiempo de tolerancia (¿ y si falla?)
      BEGIN
         --
         SELECT a.valor 
         INTO l_hora_base
         FROM parametro a
         WHERE  a.clave='HORA_ENTRADA_BASE';
         
         SELECT TO_NUMBER(a.valor)
         INTO l_tolerancia 
         FROM parametro a 
         WHERE a.clave='TOLERANCIA_MINUTOS';
      EXCEPTION WHEN NO_DATA_FOUND THEN
         --
         l_hora_base  := '09:00';
         l_tolerancia := 15;
         --
      END;
      --
      --Ahora que tenemos los parametros, entonces si calculamos si es temprano o tarde.
      --Como el l_hora_base esta en cadena lo casteamos a un timestamp( como es asistencia necesitamos la mayor cantidad de información de fecha y hora)
      l_limite_entrada :=TO_TIMESTAMP(TO_CHAR(l_fec_actual, 'YYYY-MM-DD') || ' ' || l_hora_base || ':00', 'YYYY-MM-DD HH24:MI:SS');
      --sumamos los minutos de tolerancia, al limite de entrada
      l_limite_entrada := l_limite_entrada + NUMTODSINTERVAL(l_tolerancia,'MINUTE');
      --
      IF l_hora_actual >  l_limite_entrada THEN
         --
         l_estado_asistencia :='TARDE';
         --
      ELSE
         l_estado_asistencia :='PUNTUAL';
      END IF;
      --Ahora que ya tenemos todos los campos para poblar la tabla de asistencia-->UPDATE
      INSERT INTO asistencia (id_usuario,fec_asistencia,hora_entrada,estado_asistencia)
         VALUES (p_id_usuario, l_fec_actual,l_hora_actual,l_estado_asistencia);
      COMMIT;
      
      p_resultado := 'Entrada registrada ( '||l_estado_asistencia||')';
   EXCEPTION
      WHEN OTHERS THEN
         ROLLBACK;
            p_resultado := 'Error' ||SQLERRM;
      --
   END  sp_registrar_entrada;
   ------------------------------------------------------------------------------------------
   PROCEDURE sp_registrar_salida( p_id_usuario  IN usuario.id_usuario %TYPE        ,
                                  p_resultado  OUT VARCHAR2                        )
   IS
   --
      --
      l_id_asistencia asistencia.id_asistencia  %TYPE       ;
      l_fec_actual  DATE       := obtener_fecha_asistencia();
      l_hora_actual TIMESTAMP  :=SYSTIMESTAMP               ;
      --
   --
   BEGIN 
     --Busca si el usuario en ese mismo día tiene generado un registro de entrada
      BEGIN
      SELECT a.id_asistencia 
      INTO l_id_asistencia 
      FROM asistencia a
      WHERE  a.id_usuario     = p_id_usuario
         AND a.fec_asistencia = l_fec_actual
         AND a.hora_salida    IS NULL;--Aqui verifica que no haya marcado salida
      EXCEPTION WHEN NO_DATA_FOUND THEN
         p_resultado := 'ERROR: No haz marcado asistencia hoy';
         RETURN;
      END;
      --Ahora si tiene un registro que le falta marcar salida, actualizamos el registro.
      UPDATE asistencia
      SET hora_salida     = l_hora_actual
      WHERE id_asistencia = l_id_asistencia;
      
      COMMIT;
      p_resultado:='Se registro salida con exito';
      
   EXCEPTION WHEN OTHERS THEN
      ROLLBACK;
      p_resultado:='Error: '||SQLERRM;
   END sp_registrar_salida;    
   --
--
end pkg_asistencias;