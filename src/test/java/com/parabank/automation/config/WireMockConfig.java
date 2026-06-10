package com.parabank.automation.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WireMockConfig {

    private WireMockServer wireMockServer;

    @Bean
    public WireMockServer wireMockServer() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options()
                                                                 .port(8089));
        wireMockServer.start();
        return wireMockServer;
    }
}
