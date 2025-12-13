DECLARE
    l_res VARCHAR2(100);
BEGIN
    PKG_JUSTIFICACIONES.SP_REGISTRAR_JUSTIFICACION(
        p_id_usuario => 1, 
        p_fec_incidencia => SYSDATE, 
        p_motivo => 'Llegué tarde por tráfico en la Javier Prado', 
        p_resultado => l_res
    );
    DBMS_OUTPUT.PUT_LINE(l_res);
END;
/