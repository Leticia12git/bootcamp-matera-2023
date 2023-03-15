package com.bootcamp.matera.CarteiraDigital.repository;


import com.bootcamp.matera.CarteiraDigital.model.Titular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio relacionada ao titular
 */
@Repository
public interface TitularRepository extends JpaRepository<Titular, Long> {

    // select * from conta where titular_id = parametro
//    List<Conta> findByTitularId(Long id);
}