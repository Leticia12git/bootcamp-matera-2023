package com.bootcamp.matera.CarteiraDigital.exceptions;


/**
 * Classe para exceção de valor invalido
 */
public class ValorInvalidoException extends RuntimeException{

    public ValorInvalidoException(String message) {
        super(message);
    }
}