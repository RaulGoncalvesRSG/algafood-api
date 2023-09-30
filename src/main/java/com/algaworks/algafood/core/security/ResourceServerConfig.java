package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
            .oauth2ResourceServer().jwt();          //ResourceServer faz a leitura do tipo do token especificado para verificar se assinatura é válida

        return http
                .formLogin(Customizer.withDefaults())           //Permite a tela de login para fluxo Authorization Code
                .build();
    }
}
