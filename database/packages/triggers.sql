create or replace TRIGGER trg_audit_asistencia
AFTER INSERT OR UPDATE OR DELETE ON asistencia
FOR EACH ROW
DECLARE
    v_tipo_evento VARCHAR2(20);
    v_info_old    VARCHAR2(4000); 
    v_info_new    VARCHAR2(4000);
    v_id_user     NUMBER;
BEGIN
    -- 1. Si es un registro NUEVO
    IF INSERTING THEN
        v_tipo_evento := 'INSERT';
        v_id_user     := :NEW.id_usuario;
        v_info_new    := 'Se registró entrada a las: ' || TO_CHAR(:NEW.hora_entrada, 'HH24:MI');

    -- 2. Si se ACTUALIZA 
    ELSIF UPDATING THEN
        v_tipo_evento := 'UPDATE';
        v_id_user     := :OLD.id_usuario;
        v_info_old    := 'Salida anterior: ' || TO_CHAR(:OLD.hora_salida, 'HH24:MI');
        v_info_new    := 'Salida nueva: '    || TO_CHAR(:NEW.hora_salida, 'HH24:MI');

    -- 3. Si se BORRA 
    ELSIF DELETING THEN
        v_tipo_evento := 'DELETE';
        v_id_user     := :OLD.id_usuario;
        v_info_old    := 'Se borró la asistencia del día: ' || :OLD.fec_asistencia;
    END IF;

    -- Guardamos 
    INSERT INTO tbl_log_asistencia (
        id_usuario, 
        id_asistencia, 
        tipo_evento, 
        datos_antiguos, 
        datos_nuevos, 
        fec_evento
    ) VALUES (
        v_id_user,
        COALESCE(:NEW.id_asistencia, :OLD.id_asistencia), -- Usa el ID nuevo, si no existe usa el viejo
        v_tipo_evento,
        v_info_old,
        v_info_new,
        SYSTIMESTAMP
    );
END;
/
CREATE OR REPLACE TRIGGER trg_audit_justificacion
AFTER INSERT OR UPDATE ON justificacion
FOR EACH ROW
DECLARE
    v_tipo_evento VARCHAR2(20);
    v_info_old    VARCHAR2(4000);
    v_info_new    VARCHAR2(4000);
BEGIN
    -- 1. Nueva Justificación
    IF INSERTING THEN
        v_tipo_evento := 'INSERT';
        v_info_new    := 'Motivo creado: ' || :NEW.motivo;

    -- 2. Cambio de Estado (Aprobar/Rechazar)
    ELSIF UPDATING THEN
        -- Solo guardamos log si el estado cambió
        IF :OLD.estado_solicitud != :NEW.estado_solicitud THEN
            v_tipo_evento := 'UPDATE_ESTADO';
            v_info_old    := 'Estado anterior: ' || :OLD.estado_solicitud;
            v_info_new    := 'Estado nuevo: '    || :NEW.estado_solicitud;
        ELSE
            v_tipo_evento := 'UPDATE';
            v_info_new    := 'Actualización general de datos';
        END IF;
    END IF;

    -- Insertar log
    INSERT INTO tbl_log_justificacion (
        id_usuario,
        id_justificacion,
        tipo_evento,
        datos_antiguos,
        datos_nuevos,
        fec_evento
    ) VALUES (
        :NEW.id_usuario,
        :NEW.id_justificacion,
        v_tipo_evento,
        v_info_old,
        v_info_new,
        SYSTIMESTAMP
    );
END;