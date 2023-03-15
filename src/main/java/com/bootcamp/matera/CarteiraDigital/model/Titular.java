package com.bootcamp.matera.CarteiraDigital.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade relacionada ao Titular
 */
@Entity
@Getter
@Setter
public class Titular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Endereco endereco;

    //    @JsonIgnore
    @OneToMany(mappedBy = "titular")
    private List<Conta> contas = new ArrayList<>();

}
