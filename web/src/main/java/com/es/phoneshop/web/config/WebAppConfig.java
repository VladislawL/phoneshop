package com.es.phoneshop.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebAppConfig {

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    @AjaxErrorMessageQualifier
    public Map<String, Object> errorMessage() {
        return new HashMap<>();
    }

}
