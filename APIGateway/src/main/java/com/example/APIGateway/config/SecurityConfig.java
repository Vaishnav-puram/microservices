package com.example.APIGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity serverHttpSecurity){
                serverHttpSecurity.authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2Client()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return serverHttpSecurity.build();
//        ServerHttpSecurity.OAuth2ResourceServerSpec<ServerHttpSecurity> oauth2ResourceServerSpec = serverHttpSecurity.oauth2ResourceServer().jwt();
//        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec = serverHttpSecurity.authorizeExchange();
//
//        authorizeExchangeSpec
//                .anyExchange().authenticated();
//
//        return serverHttpSecurity.build();

    }
}
