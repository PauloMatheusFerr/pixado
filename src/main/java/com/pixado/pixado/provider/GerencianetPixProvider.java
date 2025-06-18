
package com.pixado.pixado.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixado.pixado.dto.PixPayloadResult;
import com.pixado.pixado.model.Transacao;
import com.pixado.pixado.model.Usuario;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Profile("!test")
public class GerencianetPixProvider implements PixProvider {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PixPayloadResult gerarPayload(Usuario usuario, Transacao transacao) {
        try {
            String accessToken = obterAccessToken(usuario);
            String txid = UUID.randomUUID().toString().replace("-", "").substring(0, 26);
            String valorFormatado = String.format("%.2f", transacao.getValor()).replace(",", ".");

            Map<String, Object> cobranca = new HashMap<>();
            cobranca.put("calendario", Map.of("expiracao", 3600));
            cobranca.put("valor", Map.of("original", valorFormatado));
            cobranca.put("chave", usuario.getChavePix());
            cobranca.put("solicitacaoPagador", transacao.getDescricao());

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(cobranca), headers);
            RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory(usuario.getCaminhoCertificado()));

            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    "https://pix.api.efipay.com.br/v2/cob/" + txid,
                    HttpMethod.PUT,
                    request,
                    JsonNode.class
            );

            String locId = response.getBody().get("loc").get("id").asText();

            ResponseEntity<JsonNode> qrResponse = restTemplate.exchange(
                    "https://pix.api.efipay.com.br/v2/loc/" + locId + "/qrcode",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    JsonNode.class
            );

            String qrcode = qrResponse.getBody().get("qrcode").asText();
            String imagem = qrResponse.getBody().get("imagemQrcode").asText();

            return new PixPayloadResult(txid, qrcode, imagem);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar payload Gerencianet", e);
        }
    }


    @Override
    public String gerarQRCode(String payload) {
        return payload;
    }

    @Override
    public boolean verificarPagamento(String txid, Usuario usuario) {
        try {
            String accessToken = obterAccessToken(usuario);
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory(usuario.getCaminhoCertificado()));
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    "https://pix.api.efipay.com.br/v2/cob/" + txid,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    JsonNode.class
            );

            JsonNode body = response.getBody();

            // O campo "pix" indica se houve pagamento
            if (body != null && body.has("pix") && body.get("pix").isArray() && body.get("pix").size() > 0) {
                return true; // Foi pago
            }

            return false; // Ainda não foi pago
        } catch (Exception e) {
            e.printStackTrace(); // Para debugar
            return false;
        }
    }


    private String obterAccessToken(Usuario usuario) throws Exception {
        String auth = usuario.getClientId() + ":" + usuario.getClientSecret();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=client_credentials";
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory(usuario.getCaminhoCertificado()));

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                "https://pix.api.efipay.com.br/oauth/token",
                HttpMethod.POST,
                request,
                JsonNode.class
        );

        return response.getBody().get("access_token").asText();
    }
}
