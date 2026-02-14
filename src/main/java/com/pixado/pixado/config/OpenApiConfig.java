package com.pixado.pixado.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pixadoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pixado API")
                        .description("API para gerar QR Code de pagamento via Pix, verificar status de transações e organizar relatórios por chave Pix e cliente")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Paulo Matheus Ferreira da Silva")
                                .email("paulomatheusferr@gmail.com")
                                .url("https://github.com/xenobil"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
