package com.bootcamp.matera.CarteiraDigital.model;

import com.bootcamp.matera.CarteiraDigital.dto.response.ContaResponseDTO;
import com.bootcamp.matera.CarteiraDigital.dto.request.PixBacenDTO;
import com.bootcamp.matera.CarteiraDigital.exceptions.SaldoInsuficienteException;
import com.bootcamp.matera.CarteiraDigital.exceptions.ValorInvalidoException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Data
@Builder
@Log4j2
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int agencia;
    private int numero = new Random().nextInt(100000);
    private BigDecimal saldo = BigDecimal.ZERO;
    private String pix;
    @ManyToOne
    private Banco banco;

    @CreationTimestamp
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    private LocalDateTime dataUltimaAtualizacao;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "nome_coluna_titular_id")
    private Titular titular;

    @ManyToMany
    @JoinTable(name = "conta_tipos_tarifa", joinColumns = @JoinColumn(name = "conta_id"), inverseJoinColumns = @JoinColumn(name = "tipo_tarifa_id"))
    private List<TipoTarifa> tiposTarifa = new ArrayList<>();

    public Conta() {
    }


    //credito, debito
    public void credito(BigDecimal valor) {
        this.validar(valor);
        saldo = saldo.add(valor);
        log.info("Conta {}/{} foi creditada com {} valor.", this.agencia, this.numero, valor);
    }

    public void debito(BigDecimal valor) {
        this.validar(valor);
        // -1 se for menor
        // 0 se for igual
        // 1 se for maior

        // valor = 100
        // saldo = 250
        // 100.compareTo(250) > 0 -> -1
        //if (-1 > 0) { nao entro }

        // valor = 100
        // saldo = 50

        // 100.compareTo(50) > 0 -> 1
        //if (1 > 0) { sim entro }

        if (valor.compareTo(saldo) > 0) {
            throw new SaldoInsuficienteException("Conta não tem saldo para atender a solicitacao");
        }
        saldo = saldo.subtract(valor);
        log.info("Conta {}/{} foi debitada com {} valor.", this.agencia, this.numero, valor);
    }

    /**
     * este metodo...
     *
     * @param valor
     */
    private void validar(BigDecimal valor) {
        final String mensagem = String.format("O valor %s é inválido.", valor);
        if (valor == null) {
            throw new ValorInvalidoException(mensagem);
        }

        if (this.valorIncorreto(valor)) {
            throw new ValorInvalidoException(mensagem);
        }
    }

    private boolean valorIncorreto(BigDecimal valor) {
        //NPE
        return valor.compareTo(BigDecimal.ZERO) <= 0;
    }

    public ContaResponseDTO toContaDto() {
        ContaResponseDTO dto = new ContaResponseDTO();
        dto.setAgencia(this.getAgencia());
        dto.setNumero(this.getNumero());
        dto.setSaldo(this.getSaldo());
        dto.setChavePix(this.getPix());
        return dto;
    }

    public PixBacenDTO toBacenDto() {
        PixBacenDTO dto = new PixBacenDTO();
        dto.setAgencia(this.agencia);
        dto.setNumero(this.numero);
        dto.setChave(this.pix);
        dto.setCpf(this.titular.getCpf());
        return dto;
    }

}
