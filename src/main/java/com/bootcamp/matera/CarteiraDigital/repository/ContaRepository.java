package com.bootcamp.matera.CarteiraDigital.repository;

import com.bootcamp.matera.CarteiraDigital.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio relacionada a Conta
 */

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByAgenciaAndNumero(int agencia, int numero);

    Optional<Conta> findByPix(String chavePix);

}