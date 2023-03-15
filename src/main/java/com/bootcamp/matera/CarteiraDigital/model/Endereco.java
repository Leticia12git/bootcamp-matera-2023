package com.bootcamp.matera.CarteiraDigital.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entidade relacionada ao Endereço
 */
@Getter
@Setter
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;
    private String numero;

    @OneToOne(mappedBy = "endereco")
    private Titular titular;

}
