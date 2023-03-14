package com.bootcamp.matera.CarteiraDigital.controller;

import com.bootcamp.matera.CarteiraDigital.model.TipoTarifa;
import com.bootcamp.matera.CarteiraDigital.repository.TipoTarifaRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe de endpoints da Tarifa da Conta
 */

@Data
@RestController
@RequestMapping("/v1/tarifas")
public class TarifaController {


    @Autowired
    private TipoTarifaRepository tipoTarifaRepository;

    @GetMapping
    public TipoTarifa procuraPorNome(@RequestParam String nome) {
        return tipoTarifaRepository.findByNomeContaining(nome);
    }
}
