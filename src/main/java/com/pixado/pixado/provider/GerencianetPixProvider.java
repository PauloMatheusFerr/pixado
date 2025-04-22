package com.pixado.pixado.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixado.pixado.model.Transacao;
import com.pixado.pixado.model.Usuario;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Profile("!test")
public class GerencianetPixProvider implements PixProvider {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String gerarPayload(Usuario usuario, Transacao transacao) {
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
            RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());

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

            return qrResponse.getBody().get("qrcode").asText();
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

            RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    "https://pix.api.efipay.com.br/v2/pix/" + txid,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    JsonNode.class
            );
            return response.getBody().get("status").asText().equalsIgnoreCase("CONCLUIDA");
        } catch (Exception e) {
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
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                "https://pix.api.efipay.com.br/oauth/token",
                HttpMethod.POST,
                request,
                JsonNode.class
        );

        return response.getBody().get("access_token").asText();
    }

    static class HttpsClientRequestFactory extends HttpComponentsClientHttpRequestFactory {
        public HttpsClientRequestFactory() throws Exception {
            char[] senha = "".toCharArray(); // senha padrão (vazia) para certificado sandbox

            InputStream certificadoStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("certificados/Testando Produção.p12");

            if (certificadoStream == null) {
                throw new RuntimeException("Certificado não encontrado em resources/certificados.");
            }

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(certificadoStream, senha);

            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, senha)
                    .build();

            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslSocketFactory)
                    .build();

            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();

            this.setHttpClient(httpClient);

        }
    }
}
