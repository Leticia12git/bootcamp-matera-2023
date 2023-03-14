package com.bootcamp.matera.CarteiraDigital.repository;

import com.bootcamp.matera.CarteiraDigital.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {

    Optional<Banco> findByCodigo(int codigo);
}