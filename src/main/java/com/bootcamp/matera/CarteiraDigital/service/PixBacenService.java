package com.bootcamp.matera.CarteiraDigital.service;

import com.bootcamp.matera.CarteiraDigital.dto.request.PixBacenDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Classe de servico do Pix
 */
@Slf4j
@Service
public class PixBacenService {

    private static final String CONTAS_BACEN_URL = "http://localhost:8081/v1/contas";

    private static final  RestTemplate restTemplate = new RestTemplate();

    /**
     * metodo para cadastrar o pix
     *
     * @param pixBacenDTO
     */
    public void cadastraPixBancoCentral(PixBacenDTO pixBacenDTO) {
        HttpEntity<PixBacenDTO> request = new HttpEntity<>(pixBacenDTO);
        PixBacenDTO pixBacenDto = restTemplate.postForObject(CONTAS_BACEN_URL,
                request, PixBacenDTO.class);
        log.info("Conta cadastrada com sucesso no BACEN: {}", pixBacenDto);
    }

    /**
     * metodo para buscar conta no banco central
     *
     * @param chavePix
     * @return PixBacenDTO
     */
    public PixBacenDTO buscarContaBancoCentral(String chavePix) {
        String url = String.format("%s/%s", CONTAS_BACEN_URL, chavePix);
        PixBacenDTO pixBacenDto = restTemplate.getForObject(url, PixBacenDTO.class);
        log.info("Conta encontrada com sucesso no BACEN: {}", pixBacenDto);
        return pixBacenDto;
    }

}