package com.bootcamp.matera.CarteiraDigital.dto.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Classe de requisição da conta
 */
@Data
public class ContaDTO {

    private int agencia;
    private int numero;
    private BigDecimal saldo;
}
