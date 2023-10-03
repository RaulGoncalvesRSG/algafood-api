package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class ResourceServerConfig {

    @Bean       //Este método é acessado todas vezes que é feito uma requisição na API
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults())   //Configuração necessária apenas se usar o AS dentro do Resource Server (a Rest API)
            .csrf().disable()
            .cors().and()       //Ativa cors
            .oauth2ResourceServer()
                .jwt()          //ResourceServer faz a leitura do tipo do token especificado para verificar se assinatura é válida
                .jwtAuthenticationConverter(jwtAuthenticationConverter());

        /*OBS: O retorno http do ResourceServerConfig precisa ser o IGUAL ao AuthorizationServerConfig, pois AS e RS estão integrados no mesmo projeto
        Se as duas aplicações estiverem separadas, essa configuração do formLogin no ResourceServerConfig n seria necessária*/
        return http.build();
    }

    //Lê informações customizadas do JWT
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            //"authorities" é um campo costumizado no AuthorizationServerConfig
            List<String> authorities = jwt.getClaimAsStringList("authorities");

            //JwtGrantedAuthoritiesConverter converte os Scopes do JWT em Authority
            JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> grantedAuthorities = authoritiesConverter.convert(jwt);

            //Se for Client Critentials, a authorities é null. Logo, deve retornar grantedAuthorities que contém os SCOPES
            if (Objects.isNull(authorities)) {
                return grantedAuthorities;             //Evita erro de serialização durante a leitura do token
            }

            //Combina as duas listas: a que vem dos scopes de JWT (grantedAuthorities) e as que vem dos "authorities" do JWT
            grantedAuthorities.addAll(authorities
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()));

            return grantedAuthorities;
        });
        return converter;
    }
}
