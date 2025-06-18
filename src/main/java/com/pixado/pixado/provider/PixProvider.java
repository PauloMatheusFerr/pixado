package com.pixado.pixado.provider;

import com.pixado.pixado.dto.PixPayloadResult;
import com.pixado.pixado.model.Transacao;
import com.pixado.pixado.model.Usuario;

public interface PixProvider {

    // Agora retorna um DTO contendo txid e payload
    PixPayloadResult gerarPayload(Usuario usuario, Transacao transacao);

    String gerarQRCode(String payload);

    boolean verificarPagamento(String txid, Usuario usuario);
}
