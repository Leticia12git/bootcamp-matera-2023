package com.bootcamp.matera.CarteiraDigital.exceptions;


/**
 * Classe para exceção de conta inexistente
 */
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContaInexistenteException extends RuntimeException {

    public ContaInexistenteException(String message) {
        super(message);
    }
}