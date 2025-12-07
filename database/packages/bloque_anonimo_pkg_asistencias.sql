SET SERVEROUTPUT ON;
DECLARE
    v_id_usuario NUMBER;
    v_resultado  VARCHAR2(200);
BEGIN
    -- Buscar el ID del usuario 
    BEGIN
        SELECT ID_USUARIO 
        INTO v_id_usuario 
        FROM usuario 
        WHERE USERNAME = 'adminIndra';
    EXCEPTION WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Error: El usuario no existe.');
        RETURN;
    END;

    -- Llamar al procedimiento del paquete para registrar entrada
    DBMS_OUTPUT.PUT_LINE('Intentando registrar entrada para ID: ' || v_id_usuario);
    
    PKG_ASISTENCIAS.SP_REGISTRAR_ENTRADA(
        p_id_usuario => v_id_usuario,
        p_resultado  => v_resultado
    );

    -- Mostramos el resultado
    DBMS_OUTPUT.PUT_LINE(v_resultado);
END;
-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------

SET SERVEROUTPUT ON;
DECLARE
    v_id_usuario NUMBER;
    v_resultado  VARCHAR2(200);
BEGIN
    --Buscar el ID
    SELECT ID_USUARIO 
    INTO v_id_usuario 
    FROM usuario 
    WHERE USERNAME = 'adminIndra';

    --Llamar al procedimiento del paquete para registrar la salida
    PKG_ASISTENCIAS.SP_REGISTRAR_SALIDA(
        p_id_usuario => v_id_usuario,
        p_resultado  => v_resultado
    );

    -- Mostramos el resultado
    DBMS_OUTPUT.PUT_LINE(v_resultado);

END;
