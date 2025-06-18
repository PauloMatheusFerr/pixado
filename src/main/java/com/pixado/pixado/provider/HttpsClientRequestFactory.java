package com.pixado.pixado.provider;

import com.pixado.pixado.model.Usuario;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

public class HttpsClientRequestFactory extends HttpComponentsClientHttpRequestFactory {
    public HttpsClientRequestFactory(String caminhoCertificado) throws Exception {
        char[] senha = "".toCharArray(); // senha padrão (vazia) para certificado sandbox ou produção

        InputStream certificadoStream;

        if (caminhoCertificado.startsWith("/") || caminhoCertificado.contains(":")) {
            // Caminho absoluto (Windows ou Linux)
            certificadoStream = new FileInputStream(caminhoCertificado);
        } else {
            // Caminho relativo ao classpath
            certificadoStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(caminhoCertificado);
        }

        if (certificadoStream == null) {
            throw new RuntimeException("Certificado não encontrado em: " + caminhoCertificado);
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
