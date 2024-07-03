package com.ucoban.medsoft.accountserver.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class KeyCloakConfig {
    @Value("${keycloak-admin.realm}")
    private String realm;
    @Value("${keycloak-admin.server-url}")
    private String url;
    @Value("${keycloak-admin.client-id}")
    private String clientId;
    @Value("${keycloak-admin.client-secret}")
    private String clientSecret;
    @Value("${keycloak-admin.admin-user}")
    private String adminUsername;
    @Value("${keycloak-admin.admin-password}")
    private String adminPassword;

    @Bean("key-cloak-builder")
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .realm(realm)
                .username(adminUsername)
                .password(adminPassword)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .serverUrl(url)
                .build();
    }

}
