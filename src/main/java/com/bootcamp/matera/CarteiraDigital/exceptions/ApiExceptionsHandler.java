package com.bootcamp.matera.CarteiraDigital.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Classe de exceção para validar conta e saldo
 */
@ControllerAdvice
public class ApiExceptionsHandler {

    @ExceptionHandler(ContaInexistenteException.class)
    public ResponseEntity trataContaInexistente(ContaInexistenteException e) {
        Problema problema = new Problema(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problema);
    }

    @ExceptionHandler(ValorInvalidoException.class)
    public ResponseEntity valorInvalido(ValorInvalidoException e) {
        Problema problema = new Problema(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(problema);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity saldoInsuficiente(SaldoInsuficienteException e) {
        Problema problema = new Problema(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(problema);
    }
}
