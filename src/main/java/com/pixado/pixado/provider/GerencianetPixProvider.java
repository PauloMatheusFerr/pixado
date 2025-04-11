package com.pixado.pixado.provider;

import com.pixado.pixado.model.Transacao;
import com.pixado.pixado.model.Usuario;

public class GerencianetPixProvider implements PixProvider {

    @Override
    public String gerarPayload(Usuario usuario, Transacao transacao) {
        // Mock de payload Pix dinâmico com txid (poderia integrar com SDK real)
        return "0002012636BR.GOV.BCB.PIX0114" + usuario.getChavePix() + "5204000053039865404" + transacao.getValor() + "5802BR5920" + transacao.getNomeCliente() + "6009SAO PAULO62100506PIXADOTXID";
    }

    @Override
    public String gerarQRCode(String payload) {
        return "data:image/png;base64,..."; // mock de QR gerado
    }

    @Override
    public boolean verificarPagamento(String txid, Usuario usuario) {
        return false; // mock
    }
}