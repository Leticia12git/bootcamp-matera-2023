package com.bootcamp.matera.CarteiraDigital.exceptions;


/**
 * Classe para exceção de saldo insuficiente
 */
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(String message) {
        super(message);
    }
}