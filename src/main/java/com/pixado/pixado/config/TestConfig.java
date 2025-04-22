package com.pixado.pixado.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.client.HttpClientAutoConfiguration;

@ImportAutoConfiguration(exclude = HttpClientAutoConfiguration.class)
public class TestConfig {
}
