package com.bootcamp.matera.CarteiraDigital.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaRequestDTO {

    private int agencia;
    private String nome;
    private String cpf;
    private int codigo;
    private String chave;
}
