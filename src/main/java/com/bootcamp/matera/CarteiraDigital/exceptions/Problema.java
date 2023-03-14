package com.bootcamp.matera.CarteiraDigital.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Problema {

    private LocalDateTime dataHora = LocalDateTime.now();
    private String mensagem;

    public Problema(String mensagem) {
        this.mensagem = mensagem;
    }
}