package com.pixado.pixado.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagamentoStatusDTO {
    private String status;
    private BigDecimal valor;
    private String descricao;
    private LocalDateTime dataCriacao;
    private String txid;

    // Construtor
    public PagamentoStatusDTO(String status, BigDecimal valor, String descricao, LocalDateTime dataCriacao, String txid) {
        this.status = status;
        this.valor = valor;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.txid = txid;
    }

    // Getters e Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getTxid() { return txid; }
    public void setTxid(String txid) { this.txid = txid; }
}
