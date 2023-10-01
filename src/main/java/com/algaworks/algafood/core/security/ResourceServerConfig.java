package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)     //Sem o parâmetro prePostEnable=true, as anotações de PostFilter e PreFilter não funcionam
@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/oauth2/**").authenticated()
            .and()
            .csrf().disable()
            .cors().and()       //Ativa cors
            .oauth2ResourceServer()
                .jwt()          //ResourceServer faz a leitura do tipo do token especificado para verificar se assinatura é válida
                .jwtAuthenticationConverter(jwtAuthenticationConverter());

        /*OBS: O retorno http do ResourceServerConfig precisa ser o IGUAL ao AuthorizationServerConfig, pois AS e RS estão integrados
        Se as duas aplicações estiverem separadas, essa configuração do formLogin no ResourceServerConfig n é necessária*/
        return http.formLogin(customizer -> customizer.loginPage("/login")).build();
    }


    //Lê  informações customizadas do JWT
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            //"authorities" é um campo costumizado no AuthorizationServerConfig
            List<String> authorities = jwt.getClaimAsStringList("authorities");

            if (Objects.isNull(authorities)) {          //Se for Client Critentials, a authorities é null
                return Collections.emptyList();             //Evita erro de serialização durante a leitura do token
            }

            //JwtGrantedAuthoritiesConverter converte os Scopes do JWT em Authority
            JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> grantedAuthorities = authoritiesConverter.convert(jwt);

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
