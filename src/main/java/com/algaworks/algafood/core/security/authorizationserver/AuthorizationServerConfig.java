package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;
import java.util.Arrays;

@Configuration
public class AuthorizationServerConfig {

    //Existe SecurityFilterChain para Authorization Server e outro para Resource Server, HIGHEST_PRECEDENCE garante
    // q o do AS será executado primeiro para garantir q o login e a autenticação funcione corretamente
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        //applyDefaultSecurity aplica configurações padrão do AS
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }

    //ProviderSettings - responsável por escrever qm seria o AS q vai assinar os tokens
    @Bean
    public ProviderSettings providerSettings(AlgaFoodSecurityProperties properties) {
        return ProviderSettings.builder()
                .issuer(properties.getProviderUrl())        //URL da própria aplicação
                .build();
    }

    //Guarda os clients do AS
    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {

        RegisteredClient algafoodbackend = RegisteredClient
                .withId("1")            //ID buscado no BD
                .clientId("algafood-backend")
                .clientSecret(passwordEncoder.encode("backend123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("READ")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(30))      //Tempo de autenticação
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(Arrays.asList(algafoodbackend));
    }

    @Bean       //Configuração para armazenar autorizações no BD. Pega por parâmetro o @Bean RegisteredClientRepository
    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }
}
