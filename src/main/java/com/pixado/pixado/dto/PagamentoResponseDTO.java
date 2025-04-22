package com.pixado.pixado.dto;

import java.util.UUID;

public class PagamentoResponseDTO {
    private UUID id;
    private String status;
    private String qrCodePayload;
    private String qrCodeImagem;

    public PagamentoResponseDTO(UUID id, String status, String qrCodePayload, String qrCodeImagem) {
        this.id = id;
        this.status = status;
        this.qrCodePayload = qrCodePayload;
        this.qrCodeImagem = qrCodeImagem;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
