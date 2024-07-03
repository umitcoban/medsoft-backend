package com.ucoban.medsoft.gatewayserver.config;

import com.ucoban.medsoft.gatewayserver.util.KeycloakRoleConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${spring.cloud.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers(HttpMethod.POST, "/medsoft/accounts/api").permitAll()
                                .pathMatchers("/medsoft/accounts/api/findAllAccount").hasAnyRole("ADMIN")
                                .pathMatchers("/medsoft/accounts/api/analytics/**").hasAnyRole("ADMIN")
                                .pathMatchers("/medsoft/accounts/api/**").authenticated()
                                .pathMatchers("/medsoft/documents/api/**").authenticated()
                                .pathMatchers("/medsoft/departments/api/**").hasRole("ADMIN"))
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        serverHttpSecurity.cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()));
        return serverHttpSecurity.build();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri)
                .webClient(WebClient.builder().build())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Köken desenlerine izin ver
        configuration.setAllowedOriginPatterns(Collections.singletonList("*")); // Tüm kökenlere izin vermek için desen
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // İzin verilen HTTP metodları
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With")); // İzin verilen başlıklar
        configuration.setAllowCredentials(true); // Kimlik bilgilerine izin ver
        configuration.setMaxAge(3600L); // Önbellek süresini belirle

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Tüm yollar için bu CORS yapılandırmasını uygula
        return source;
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter
                (new KeycloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
