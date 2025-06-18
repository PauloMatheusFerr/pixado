package com.pixado.pixado.dto;

public class PixPayloadResult {

    private String txid;
    private String payload;
    private String imagemBase64;

    public PixPayloadResult(String txid, String payload, String imagemBase64) {
        this.txid = txid;
        this.payload = payload;
        this.imagemBase64 = imagemBase64;
    }

    public String getTxid() {
        return txid;
    }

    public String getPayload() {
        return payload;
    }

    public String getImagemBase64() {
        return imagemBase64;
    }
}
