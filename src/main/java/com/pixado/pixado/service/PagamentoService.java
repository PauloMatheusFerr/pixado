package com.pixado.pixado.service;

import com.pixado.pixado.dto.PagamentoRequestDTO;
import com.pixado.pixado.dto.PagamentoResponseDTO;
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
        //transacao.setNomeCliente(dto.getNomeCliente());
        // Removido por enquanto, pode adicionar depois se quiser personalizar a cobrança


        PixProvider provider = pixProviders.get(usuario.getBanco());
        if (provider == null) {
            throw new RuntimeException("Provedor Pix não disponível para o banco: " + usuario.getBanco());
        }

        String payload = provider.gerarPayload(usuario, transacao);
        String imagem = provider.gerarQRCode(payload);

        Pagamento pagamento = new Pagamento();
        pagamento.setDescricao(dto.getDescricao());
        pagamento.setValor(dto.getValor());
        pagamento.setStatus(StatusPagamento.AGUARDANDO);
        pagamento.setUsuario(usuario);
        pagamento.setQrCodePayload(payload);
        pagamento.setQrCodeImagem(imagem);
        pagamento.setIdTransacao(transacao.getId().toString());

        pagamentoRepository.save(pagamento);

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getStatus().name(),
                pagamento.getQrCodePayload(),
                pagamento.getQrCodeImagem()
        );
    }
}