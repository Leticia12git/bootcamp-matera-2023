package com.bootcamp.matera.CarteiraDigital.controller;

import com.bootcamp.matera.CarteiraDigital.dto.request.ContaDTO;
import com.bootcamp.matera.CarteiraDigital.dto.request.ContaRequestDTO;
import com.bootcamp.matera.CarteiraDigital.dto.request.PixDTO;
import com.bootcamp.matera.CarteiraDigital.dto.response.ContaResponseDTO;
import com.bootcamp.matera.CarteiraDigital.exceptions.ContaInexistenteException;
import com.bootcamp.matera.CarteiraDigital.model.Conta;
import com.bootcamp.matera.CarteiraDigital.repository.ContaRepository;
import com.bootcamp.matera.CarteiraDigital.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Classe de endpoints da Conta
 */

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1/contas")
public class ContaController {

    private final ContaRepository contaRepository;
    private final ContaService contaService;

    /**
     * endpoint para criar conta
     *
     * @param requestDto
     * @return ContaResponseDTO
     */
    @PostMapping
    public ContaResponseDTO criarConta(@RequestBody ContaRequestDTO requestDto) {
        Conta conta = contaService.criarConta(requestDto);
        return conta.toContaDTO();
    }

    /**
     * endpoint para buscar contas
     *
     * @return List<Conta>
     */
    @GetMapping()
    public List<Conta> procuraContas() {
        return contaService.procuraContas();
    }

    /**
     * endpoint para buscar contas pelo id
     *
     * @param id
     * @return ContaDTO
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContaDTO> procuraContaPorId(@PathVariable Long id) {
        try {
            Optional<Conta> contaOptional = Optional.ofNullable(contaService.procuraConta(id));
            Conta conta = contaOptional.get();
            return ResponseEntity.ok(conta.toContaDTO());
        } catch (ContaInexistenteException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * endpoint para buscar contas se usar o try/catch
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> procuraContaPorIdSemTry(@PathVariable Long id) {
        Conta conta = contaService.procuraConta(id);
        return ResponseEntity.ok(conta.toContaDTO());
    }

    /**
     * endpoint para creditar valor da conta
     *
     * @param idConta
     * @param valor
     * @return ContaResponseDTO
     */
    @PostMapping("/{idConta}/credito/{valor}")
    public ResponseEntity<ContaResponseDTO> creditarConta(@PathVariable Long idConta, @PathVariable BigDecimal valor) {
        Conta conta = contaService.creditarConta(idConta, valor);
        return ResponseEntity.ok(conta.toContaDTO());
    }

    /**
     * endpoint para debitar o valor da conta
     *
     * @param idConta
     * @param valor
     * @return
     */
    @PostMapping("/{idConta}/debito/{valor}")
    public ResponseEntity<ContaResponseDTO> debitaConta(@PathVariable Long idConta, @PathVariable BigDecimal valor) {
        Conta conta = contaService.debitaConta(idConta, valor);
        return ResponseEntity.ok(conta.toContaDTO());
    }

    /**
     * endpoint para fazer transferencia de contas
     *
     * @param idContaDebitada
     * @param idContaCreditada
     * @param valor
     * @return
     */
    @PostMapping("/{idContaDebitada}/{idContaCreditada}/{valor}")
    public ResponseEntity debitaConta(@PathVariable Long idContaDebitada, @PathVariable Long idContaCreditada, @PathVariable BigDecimal valor) {
        contaService.transferencia(idContaDebitada, idContaCreditada, valor);
        return ResponseEntity.ok("Transferencia realizada com sucesso");
    }

    /**
     * endpoint para fazer o pix entre contas
     *
     * @param idContaDebitada
     * @param chavePix
     * @param pixDto
     * @return
     */
    @PostMapping("/{idContaDebitada}/{chavePix}")
    public ResponseEntity debitaConta(@PathVariable Long idContaDebitada,
                                      @PathVariable String chavePix,
                                      @RequestBody PixDTO pixDto) {
        contaService.pix(idContaDebitada, chavePix, pixDto.getValor());
        return ResponseEntity.ok("Pix realizada com sucesso");
    }
}
