package com.bootcamp.matera.CarteiraDigital.service;

import com.bootcamp.matera.CarteiraDigital.dto.request.ContaRequestDTO;
import com.bootcamp.matera.CarteiraDigital.dto.request.PixBacenDTO;
import com.bootcamp.matera.CarteiraDigital.exceptions.BancoInexistenteException;
import com.bootcamp.matera.CarteiraDigital.exceptions.ContaExistenteException;
import com.bootcamp.matera.CarteiraDigital.exceptions.ContaInexistenteException;
import com.bootcamp.matera.CarteiraDigital.exceptions.OperacaoInvalidaException;
import com.bootcamp.matera.CarteiraDigital.model.Banco;
import com.bootcamp.matera.CarteiraDigital.model.Conta;
import com.bootcamp.matera.CarteiraDigital.model.Titular;
import com.bootcamp.matera.CarteiraDigital.repository.BancoRepository;
import com.bootcamp.matera.CarteiraDigital.repository.ContaRepository;
import com.bootcamp.matera.CarteiraDigital.repository.TitularRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe de serviços relacionados a conta
 */

@RequiredArgsConstructor
@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final TitularRepository titularRepository;

    private final BancoRepository bancoRepository;

    private final PixBacenService pixBacenService;

    /**
     * metodo para criar uma conta
     *
     * @param requestDto
     * @return Conta
     */
    public Conta criarConta(ContaRequestDTO requestDto) {

        int codigo = requestDto.getCodigo();
        final Banco banco = bancoRepository.findByCodigo(codigo).orElseThrow(() -> new BancoInexistenteException("Banco não encontrado: " + codigo));

        final Titular titular = new Titular();
        titular.setCpf(requestDto.getCpf());
        titular.setNome(requestDto.getNome());
        titularRepository.save(titular);

        var conta = new Conta();
        conta.setAgencia(requestDto.getAgencia());
        conta.setTitular(titular);
        conta.setBanco(banco);
        conta.setPix(requestDto.getChave());
        validaContaExistente(conta);
        Conta contaSalva = contaRepository.save(conta);
        pixBacenService.cadastraPixBancoCentral(contaSalva.toBacenDto());
        return contaSalva;
    }

    /**
     * metodo para validar se existe uma conta
     *
     * @param conta
     */

    private void validaContaExistente(Conta conta) {
        Optional<Conta> contaOptional = contaRepository.findByAgenciaAndNumero(conta.getAgencia(), conta.getNumero());

        if (contaOptional.isPresent()) {
            throw new ContaExistenteException();
        }
    }

    /**
     * metodo para listar contas
     *
     * @return List<Conta>
     */
    public List<Conta> procuraContas() {
        return contaRepository.findAll();
    }

    /**
     * metodo para procurar contas pelo id
     *
     * @param id
     * @return Conta
     */
    public Conta procuraConta(Long id) {
        Optional<Conta> contaOptional = contaRepository.findById(id);
        if (contaOptional.isEmpty()) {
            throw new ContaInexistenteException("Essa conta não existe!");
        }
        return contaOptional.get();
    }

    /**
     * metodo para creditar o valor da conta
     *
     * @param idConta
     * @param valor
     * @return Conta
     */
    public Conta creditarConta(Long idConta, BigDecimal valor) {
        Conta conta = procuraConta(idConta);
        conta.credito(valor);
        return contaRepository.save(conta);
    }

    /**
     * metodo para debitar o valor da conta
     *
     * @param idConta
     * @param valor
     * @return
     */
    public Conta debitaConta(Long idConta, BigDecimal valor) {
        Conta conta = procuraConta(idConta);
        conta.debito(valor);
        return contaRepository.save(conta);
    }

    /**
     * metodo para transferencia entre contas
     *
     * @param idContaDebitada
     * @param idContaCreditada
     * @param valor
     */
    public void transferencia(Long idContaDebitada, Long idContaCreditada, BigDecimal valor) {

        Conta contaDebitada = procuraConta(idContaDebitada);
        Conta contaCreditada = procuraConta(idContaCreditada);

        validarTransferencia(contaDebitada, contaCreditada);

        contaDebitada.debito(valor);
        contaCreditada.credito(valor);
        List<Conta> contas = new ArrayList<>();
        contas.add(contaCreditada);
        contas.add(contaDebitada);
        contaRepository.saveAll(contas);
    }

    /**
     * metodo para validar transferencia entre contas
     *
     * @param contaDebitada
     * @param contaCreditada
     */
    private static void validarTransferencia(Conta contaDebitada, Conta contaCreditada) {
        if (contaDebitada.getBanco().getCodigo() != contaCreditada.getBanco().getCodigo()) {
            throw new OperacaoInvalidaException();
        }
    }

    /**
     * metodo para fazer o pix
     *
     * @param idContaDebitada
     * @param chavePix
     * @param valor
     */
    public void pix(Long idContaDebitada, String chavePix, BigDecimal valor) {
        final Conta contaDebitada = procuraConta(idContaDebitada);

        PixBacenDTO pixBacenDto = pixBacenService.buscarContaBancoCentral(chavePix);

        Conta contaCreditada = contaRepository.findByAgenciaAndNumero(pixBacenDto.getAgencia(), pixBacenDto.getNumero())
                .orElseThrow(() -> new ContaInexistenteException("Conta não encontrada: " + chavePix));

        contaDebitada.debito(valor);
        contaCreditada.credito(valor);
        List<Conta> contas = new ArrayList<>();
        contas.add(contaCreditada);
        contas.add(contaDebitada);
        contaRepository.saveAll(contas);
    }
}