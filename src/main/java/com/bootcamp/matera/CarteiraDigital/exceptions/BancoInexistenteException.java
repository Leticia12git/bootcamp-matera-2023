package com.bootcamp.matera.CarteiraDigital.exceptions;

/**
 * Classe de exceção para banco inexistente
 */
public class BancoInexistenteException extends RuntimeException {

    public BancoInexistenteException(String message) {
        super(message);
    }
}
