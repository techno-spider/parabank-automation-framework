package com.parabank.automation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "parabank")
public class ParabankProperties {

    private Ui ui = new Ui();
    private Api api = new Api();
    private String browser;
    private boolean headless;
    private int timeout;

    @Data
    public static class Ui {
        private String url;
    }

    @Data
    public static class Api {
        private String baseurl;
    }
}