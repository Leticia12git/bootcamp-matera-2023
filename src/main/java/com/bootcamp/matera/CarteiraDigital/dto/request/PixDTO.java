package com.bootcamp.matera.CarteiraDigital.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Classe de requisição do pix
 */
@Getter
@Setter
public class PixDTO {
    private BigDecimal valor;

}
