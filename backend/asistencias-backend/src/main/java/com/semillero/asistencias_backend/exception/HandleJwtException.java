package com.semillero.asistencias_backend.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HandleJwtException extends RuntimeException {

    public HandleJwtException(String message) {
        super(message);
    }    

}