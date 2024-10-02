package com.proyecto.daw.exceptions;

public class ValidacionRegisterUsuario extends RuntimeException {
    public ValidacionRegisterUsuario() {
    }

    public ValidacionRegisterUsuario(String message) {
        super(message);
    }

    public ValidacionRegisterUsuario(String message, Throwable cause) {
        super(message, cause);
    }
}
