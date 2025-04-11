package com.pixado.pixado.service;
import com.pixado.pixado.dto.TransacaoRequestDTO;
import com.pixado.pixado.dto.TransacaoResponseDTO;
import com.pixado.pixado.model.Transacao;
import com.pixado.pixado.model.Usuario;
import com.pixado.pixado.provider.PixProvider;
import com.pixado.pixado.provider.PixProviderFactory;
import com.pixado.pixado.repository.TransacaoRepository;
import com.pixado.pixado.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public TransacaoResponseDTO gerarTransacao(TransacaoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.idUsuario).orElseThrow();

        Transacao transacao = new Transacao();
        transacao.setUsuario(usuario);
        transacao.setNomeCliente(dto.nomeCliente);
        transacao.setChavePix(dto.chavePix);
        transacao.setDescricao(dto.descricao);
        transacao.setValor(dto.valor);
        transacao.setStatus("PENDENTE");
        transacao.setDataCriacao(LocalDateTime.now());
        repository.save(transacao);

        PixProvider provider = PixProviderFactory.getProvider(usuario.getBanco());
        String payload = provider.gerarPayload(usuario, transacao);
        String qrCode = provider.gerarQRCode(payload);

        TransacaoResponseDTO response = new TransacaoResponseDTO();
        response.idTransacao = transacao.getId();
        response.payload = payload;
        response.qrCode = qrCode;
        response.status = transacao.getStatus();
        response.valor = transacao.getValor();
        response.nomeCliente = transacao.getNomeCliente();
        return response;
    }

    public TransacaoResponseDTO verificarStatus(UUID id) {
        Transacao transacao = repository.findById(id).orElseThrow();
        TransacaoResponseDTO dto = new TransacaoResponseDTO();
        dto.idTransacao = transacao.getId();
        dto.status = transacao.getStatus();
        dto.dataPagamento = transacao.getDataPagamento();
        dto.valor = transacao.getValor();
        dto.nomeCliente = transacao.getNomeCliente();
        return dto;
    }
}
