package com.algaworks.algafood.core.security.authorizationserver;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AuthorizationServerConfig {

    //Existe SecurityFilterChain para Authorization Server e outro para Resource Server, HIGHEST_PRECEDENCE garante
    // q o do AS será executado primeiro para garantir q o login e a autenticação funcione corretamente
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);      //applyDefaultSecurity aplica configurações padrão do AS
        return http
                .formLogin(Customizer.withDefaults())           //Permite a tela de login para fluxo Authorization Code
                .build();
    }

    @Bean       //ProviderSettings - responsável por escrever qm seria o AS q vai assinar os tokens
    public ProviderSettings providerSettings(AlgaFoodSecurityProperties properties) {
        return ProviderSettings.builder()
                .issuer(properties.getProviderUrl())        //URL da própria aplicação
                .build();
    }

    @Bean       //Guarda os clients do AS
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder, JdbcOperations jdbcOperations) {
//        RegisteredClient algafoodbackend = RegisteredClient
//                .withId("1")            //ID buscado no BD
//                .clientId("algafood-backend")
//                .clientSecret(passwordEncoder.encode("backend123"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .scope("READ")
//                //Especifica o tipo de token a ser gerado pelo AS (token opaco ou JWT)
//                .tokenSettings(TokenSettings.builder()      //OAuth2TokenFormat.REFERENCE (token opaco); OAuth2TokenFormat.SELF_CONTAINED (token JWT)
//                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
//                        .accessTokenTimeToLive(Duration.ofMinutes(30))      //Tempo de autenticação
//                        .build())
//                .build();
//
//        RegisteredClient algafoodWeb = RegisteredClient
//                .withId("2")
//                .clientId("algafood-web")
//                .clientSecret(passwordEncoder.encode("web123"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)       //Permite gerar o refresh token
//                .scope("READ")
//                .scope("WRITE")
//                .tokenSettings(TokenSettings.builder()
//                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
//                        .accessTokenTimeToLive(Duration.ofMinutes(15))      //TTL do ACCESS TOKEN
//                        .reuseRefreshTokens(false)          //Desabilita a reutilização do REFRESH_TOKEN
//                        .refreshTokenTimeToLive(Duration.ofDays(1))           //TTL do REFRESH TOKEN
//                        .build())
//                //Como é fluxo de AUTHORIZATION_CODE, a propriedade redirectUri é obrigatória
//                .redirectUri("http://127.0.0.1:8080/authorized")   ///authorized recupera o AUTHORIZATION_CODE
//                //Permite fazer o teste dentro do swagger. Essa URl recupera o o acess code para gerar o token JWT
//                .redirectUri("http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html")
//                .clientSettings(ClientSettings.builder()
//                        .requireAuthorizationConsent(true)      //Torna obrigatória aparecer a tela de consentimento das permissões
//                        .build())
//                .build();
//
//        RegisteredClient foodanalytics = RegisteredClient
//                .withId("3")
//                .clientId("foodanalytics")
//                .clientSecret(passwordEncoder.encode("web123"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .scope("READ")
//                .scope("WRITE")
//                .tokenSettings(TokenSettings.builder()
//                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
//                        .accessTokenTimeToLive(Duration.ofMinutes(30))
//                        .build())
//                .redirectUri("http://www.foodanalytics.local:8082")
//                .clientSettings(ClientSettings.builder()
//                        .requireAuthorizationConsent(false)
//                        .build())
//                .build();

        return new JdbcRegisteredClientRepository(jdbcOperations);
    }

    @Bean       //Configuração para armazenar autorizações no BD. Pega por parâmetro o @Bean RegisteredClientRepository
    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }

    @Bean       //Bean de configuração para JWT, par de chaves pública e privada
    public JWKSource<SecurityContext> jwkSource(JwtKeyStoreProperties properties) throws Exception {
        char[] keyStorePass = properties.getPassword().toCharArray();
        String keypairAlias = properties.getKeypairAlias();

        Resource jksLocation = properties.getJksLocation();
        InputStream inputStream = jksLocation.getInputStream();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, keyStorePass);

        RSAKey rsaKey = RSAKey.load(keyStore, keypairAlias, keyStorePass);

        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(UsuarioRepository usuarioRepository) {
        return context -> {
            Authentication authentication = context.getPrincipal();

            /*Fluxo de Client Cridentials não precisa de usuário do Spring, já que ele costomiza diretamente o client
            Client Cridentials n possui ID de usuário e permissões de Role*/
            if (authentication.getPrincipal() instanceof User) {
                User user = (User) authentication.getPrincipal();

                Usuario usuario = usuarioRepository.findByEmail(user.getUsername()).orElseThrow();

                Set<String> authorities = new HashSet<>();
                for (GrantedAuthority authority : user.getAuthorities()) {      //Authorities variam de acordo com as permissões do usuário
                    authorities.add(authority.getAuthority());
                }

                context.getClaims().claim("usuario_id", usuario.getId().toString());
                context.getClaims().claim("authorities", authorities);
            }
        };
    }
}
