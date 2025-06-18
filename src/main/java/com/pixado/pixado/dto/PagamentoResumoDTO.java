package com.pixado.pixado.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PagamentoResumoDTO {
    private UUID id;
    private String descricao;
    private Double valor;
    private String status;
    private String idTransacao;
    private LocalDateTime criadoEm;

    public PagamentoResumoDTO(UUID id, String descricao, Double valor, String status, String idTransacao, LocalDateTime criadoEm) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
        this.idTransacao = idTransacao;
        this.criadoEm = criadoEm;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getIdTransacao() { return idTransacao; }
    public void setIdTransacao(String idTransacao) { this.idTransacao = idTransacao; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
