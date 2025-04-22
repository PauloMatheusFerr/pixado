package com.pixado.pixado.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue
    private UUID id;

    private String descricao;
    private Double valor;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    private String idTransacao; // retornado pela API externa
    @Column(name = "qr_code_payload")
    private String qrCodePayload; // código "copia e cola"
    private String qrCodeImagem; // base64 da imagem

    private LocalDateTime criadoEm;

    @ManyToOne
    private Usuario usuario;

    @PrePersist
    public void onCreate() {
        this.criadoEm = LocalDateTime.now();
        this.status = StatusPagamento.AGUARDANDO;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(String idTransacao) {
        this.idTransacao = idTransacao;
    }

    public String getQrCodePayload() {
        return qrCodePayload;
    }

    public void setQrCodePayload(String qrCodePayload) {
        this.qrCodePayload = qrCodePayload;
    }

    public String getQrCodeImagem() {
        return qrCodeImagem;
    }

    public void setQrCodeImagem(String qrCodeImagem) {
        this.qrCodeImagem = qrCodeImagem;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}