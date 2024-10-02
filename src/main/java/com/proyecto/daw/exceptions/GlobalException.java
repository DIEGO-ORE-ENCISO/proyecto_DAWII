package com.proyecto.daw.exceptions;

import com.proyecto.daw.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    //CONTROL DE EXCEPTION SERVIDOR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?>exception(Exception ex, HttpServletRequest request){
        ApiError apiError = new ApiError();
        apiError.setBackendMensaje(ex.getLocalizedMessage());
        apiError.setUri(request.getRequestURI());
        apiError.setMetodo(request.getMethod());
        apiError.setFecha(LocalDateTime.now());

        apiError.setMensaje("ERROR EN LA PETICION EN EL SERVIDOR");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    //CONTROL DE EXCEPTION VALIDACIONES API
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?>methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){
        ApiError apiError = new ApiError();
        apiError.setBackendMensaje(ex.getLocalizedMessage());
        apiError.setUri(request.getRequestURI());
        apiError.setMetodo(request.getMethod());
        apiError.setFecha(LocalDateTime.now());

        apiError.setMensaje("ERROR EN LA PETICION , VERIFIQUE LA VALIDACION");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
