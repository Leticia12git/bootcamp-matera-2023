package com.bootcamp.matera.CarteiraDigital.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Random;

/**
 * Entidade relacionada ao Banco
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Banco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int codigo= new Random().nextInt(1000);
    private String nome;
}
