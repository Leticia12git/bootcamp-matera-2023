package com.bootcamp.matera.CarteiraDigital.controller;

import com.bootcamp.matera.CarteiraDigital.dto.request.ContaRequestDTO;
import com.bootcamp.matera.CarteiraDigital.dto.response.ContaResponseDTO;
import com.bootcamp.matera.CarteiraDigital.dto.request.PixDTO;
import com.bootcamp.matera.CarteiraDigital.model.Conta;
import com.bootcamp.matera.CarteiraDigital.repository.ContaRepository;
import com.bootcamp.matera.CarteiraDigital.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe de endpoints da Conta
 */

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1/contas")
public class ContaController {

    private final ContaRepository contaRepository;
    private final ContaService contaService;

    @PostMapping
    public ContaResponseDTO criarConta(@RequestBody ContaRequestDTO requestDto) {
        Conta conta = contaService.criarConta(requestDto);
        return conta.toContaDto();
    }
    @GetMapping()
    public List<Conta> procuraContas() {
        return contaService.procuraContas();
    }

//    @GetMapping("/{id}")
////    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<ContaDto> procuraContaPorId(@PathVariable Long id) {
//
//        try {
//            Optional<Conta> contaOptional = contaService.procuraConta(id);
//            Conta conta = contaOptional.get();
//            //return ResponseEntity.status(HttpStatus.OK).body(conta.toContaDto());
//            //return ResponseEntity.ok().body(conta.toContaDto());
//            return ResponseEntity.ok(conta.toContaDto());
//        } catch (ContaInexistenteException exception) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> procuraContaPorIdSemTry(@PathVariable Long id) {
        Conta conta = contaService.procuraConta(id);
        return ResponseEntity.ok(conta.toContaDto());
    }

    @PostMapping("/{idConta}/credito/{valor}")
    public ResponseEntity<ContaResponseDTO> creditarConta(@PathVariable Long idConta, @PathVariable BigDecimal valor) {
        Conta conta = contaService.creditarConta(idConta, valor);
        return ResponseEntity.ok(conta.toContaDto());
    }

    @PostMapping("/{idConta}/debito/{valor}")
    public ResponseEntity<ContaResponseDTO> debitaConta(@PathVariable Long idConta, @PathVariable BigDecimal valor) {
        Conta conta = contaService.debitaConta(idConta, valor);
        return ResponseEntity.ok(conta.toContaDto());
    }

    @PostMapping("/{idContaDebitada}/{idContaCreditada}/{valor}")
    public ResponseEntity debitaConta(@PathVariable Long idContaDebitada, @PathVariable Long idContaCreditada, @PathVariable BigDecimal valor) {
        contaService.transferencia(idContaDebitada, idContaCreditada, valor);
        return ResponseEntity.ok("Transferencia realizada com sucesso");
    }

    @PostMapping("/{idContaDebitada}/{chavePix}")
    public ResponseEntity debitaConta(@PathVariable Long idContaDebitada,
                                      @PathVariable String chavePix,
                                      @RequestBody PixDTO pixDto) {
        contaService.pix(idContaDebitada, chavePix, pixDto.getValor());
        return ResponseEntity.ok("Pix realizada com sucesso");
    }
}
