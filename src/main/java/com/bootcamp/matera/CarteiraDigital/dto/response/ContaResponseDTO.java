package com.bootcamp.matera.CarteiraDigital.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ContaResponseDTO {

    private int agencia;
    private int numero;
    private BigDecimal saldo;
    private String chavePix;
}
