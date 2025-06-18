package com.pixado.pixado.service;

import com.pixado.pixado.dto.*;
import com.pixado.pixado.model.Pagamento;
import com.pixado.pixado.model.StatusPagamento;
import com.pixado.pixado.model.Transacao;
import com.pixado.pixado.model.Usuario;
import com.pixado.pixado.provider.BancoProvider;
import com.pixado.pixado.provider.PixProvider;
import com.pixado.pixado.repository.PagamentoRepository;
import com.pixado.pixado.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final Map<BancoProvider, PixProvider> pixProviders;

    public PagamentoService(
            PagamentoRepository pagamentoRepository,
            UsuarioRepository usuarioRepository,
            List<PixProvider> providers
    ) {
        this.pagamentoRepository = pagamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pixProviders = new EnumMap<>(BancoProvider.class);

        // Mapeia os provedores registrados
        for (PixProvider provider : providers) {
            if (provider.getClass().getSimpleName().contains("Gerencianet")) {
                pixProviders.put(BancoProvider.GERENCIANET, provider);
            } else if (provider.getClass().getSimpleName().contains("Mock")) {
                pixProviders.put(BancoProvider.PICPAY, provider); // por enquanto
                pixProviders.put(BancoProvider.NUBANK, provider); // por enquanto
            }
        }
    }

    public PagamentoResponseDTO gerarPagamento(PagamentoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Transacao transacao = new Transacao();
        transacao.setId(UUID.randomUUID());
        transacao.setDescricao(dto.getDescricao());
        transacao.setValor(BigDecimal.valueOf(dto.getValor()));

        PixProvider provider = pixProviders.get(usuario.getBanco());
        if (provider == null) {
            throw new RuntimeException("Provedor Pix não disponível para o banco: " + usuario.getBanco());
        }

        PixPayloadResult resultado = provider.gerarPayload(usuario, transacao);
        String payload = resultado.getPayload();
        String imagem = resultado.getImagemBase64();
        String txid = resultado.getTxid();

        Pagamento pagamento = new Pagamento();
        pagamento.setDescricao(dto.getDescricao());
        pagamento.setValor(dto.getValor());
        pagamento.setStatus(StatusPagamento.AGUARDANDO);
        pagamento.setUsuario(usuario);
        pagamento.setQrCodePayload(payload);
        pagamento.setQrCodeImagem(imagem);
        pagamento.setIdTransacao(txid);

        pagamentoRepository.save(pagamento);

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getStatus().name(),
                pagamento.getQrCodePayload(),
                pagamento.getQrCodeImagem()
        );
    }

    public boolean verificarPagamento(String txid, UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PixProvider provider = pixProviders.get(usuario.getBanco());
        if (provider == null) {
            throw new RuntimeException("Provedor Pix não disponível para o banco: " + usuario.getBanco());
        }

        return provider.verificarPagamento(txid, usuario);
    }

    public PagamentoStatusDTO verificarPagamentoDetalhado(String txid, UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Pagamento pagamento = pagamentoRepository.findByIdTransacao(txid)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        PixProvider provider = pixProviders.get(usuario.getBanco());
        if (provider == null) {
            throw new RuntimeException("Provedor não configurado");
        }

        boolean foiPago = provider.verificarPagamento(txid, usuario);
        System.out.println("Status de verificação: " + foiPago);

        if (foiPago && pagamento.getStatus() != StatusPagamento.PAGO) {
            pagamento.setStatus(StatusPagamento.PAGO);
            pagamentoRepository.save(pagamento);
        }

        return new PagamentoStatusDTO(
                pagamento.getDescricao(),
                BigDecimal.valueOf(pagamento.getValor()),
                pagamento.getStatus().name(),
                pagamento.getCriadoEm(),
                pagamento.getQrCodePayload()
        );
    }

    public List<PagamentoResumoDTO> listarPagamentosPorUsuario(UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return pagamentoRepository.findAllByUsuario(usuario).stream()
                .map(p -> new PagamentoResumoDTO(
                        p.getId(),
                        p.getDescricao(),
                        p.getValor(),
                        p.getStatus().name(),
                        p.getIdTransacao(),
                        p.getCriadoEm()
                ))
                .toList();
    }
}
