package com.ucoban.medsoft.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.security.Principal;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }


    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p.path("/medsoft/accounts/**")
                        .filters(f -> f.rewritePath("/medsoft/accounts/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ACCOUNTMS"))
                .route(p -> p.path("/medsoft/documents/**")
                        .filters(f -> f.rewritePath("/medsoft/documents/(?<segment>.*)", "/${segment}"))
                        .uri("lb://DOCUMENTMS"))
                .build();
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
                .defaultIfEmpty("anonymous");
    }

    @RefreshScope
    @Bean
    public GlobalFilter customFilter() {
        return (exchange, chain) -> exchange.getPrincipal()
                .flatMap(principal -> {
                    if (principal instanceof JwtAuthenticationToken jwtAuth) {
                        String userId = (String) jwtAuth.getTokenAttributes().get("sub");
                        exchange.getRequest().mutate().header("user-id", userId).build();
                    }
                    return Mono.just(exchange);
                })
                .flatMap(chain::filter);
    }

}
