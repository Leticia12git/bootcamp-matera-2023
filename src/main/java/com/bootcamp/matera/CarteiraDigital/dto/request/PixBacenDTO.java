package com.bootcamp.matera.CarteiraDigital.dto.request;


import lombok.Getter;
import lombok.Setter;

/**
 * Classe de requisição do  pix
 */
@Getter
@Setter
public class PixBacenDTO {
    private int agencia;
    private String cpf;
    private int numero;
    private String chave;


}
