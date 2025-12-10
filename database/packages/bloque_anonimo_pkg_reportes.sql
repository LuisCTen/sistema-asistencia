SET SERVEROUTPUT ON;
DECLARE
    l_cursor          SYS_REFCURSOR                        ;
    l_id              usuario.id_usuario            %TYPE  ;
    l_fec_asistencia  asistencia.fec_asistencia     %TYPE  ;
    l_hora_entrada    asistencia.hora_entrada       %TYPE  ;
    l_hora_salida     asistencia.hora_salida        %TYPE  ;
    l_estado          asistencia.estado_asistencia %TYPE  ;
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- PRUEBA REPORTE ---');

    PKG_REPORTES.SP_LISTAR_HISTORIAL(p_id_usuario   => 1        ,
                                     p_fec_inicio   => NULL     , 
                                     p_fec_fin      => NULL     , 
                                     p_cursor       => l_cursor);

    LOOP
        FETCH l_cursor INTO l_id, l_fec_asistencia, l_hora_entrada, l_hora_salida, l_estado;
        EXIT WHEN l_cursor%NOTFOUND;
        
        DBMS_OUTPUT.PUT_LINE(   'ID: '      || l_id || 
                             '  | Fecha: '   || l_fec_asistencia || 
                             ' | Entrada: ' || l_hora_entrada || 
                             ' | Estado: '  || l_estado);
    END LOOP;
    
    CLOSE l_cursor;
END;
/