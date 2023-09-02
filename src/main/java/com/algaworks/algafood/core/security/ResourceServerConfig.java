package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
//EnableGlobalMethodSecurity habita restrição de acesso nos métodos. prePostEnabled = true para @PreAuthorize funcionar
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				//.authorizeRequests()
				//.anyRequest().authenticated()		//Qualquer requisição autenticada (com qualquer role/autorização) pode acessar qualquer endpoint da API
				.csrf().disable()
				.csrf().disable()
				.cors().and()
				.oauth2ResourceServer().jwt()
				.jwtAuthenticationConverter(jwtAuthenticationConverter());
		http

				.cors().and()
			.oauth2ResourceServer().jwt()
				.jwtAuthenticationConverter(jwtAuthenticationConverter());
	}

	//Converte as claims authorities do token do usuário logado em SimpleGrantedAuthority
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			List<String> authorities = jwt.getClaimAsStringList("authorities");

			if (Objects.isNull(authorities)) {
				authorities = Collections.emptyList();
			}

			return authorities.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		});
		return jwtAuthenticationConverter;
	}

//	@Bean		//Chave secreta simétrica
//	public JwtDecoder jwtDecoder() {
//		//1 arg é um array de bytes e o 2 arg é o algoritmo para a chave secreta
//		var secretKey = new SecretKeySpec("89a7sd89f7as98f7dsa98fds7fd89sasd9898asdf98s".getBytes(), "HmacSHA256");
//		return NimbusJwtDecoder.withSecretKey(secretKey).build();
//	}
}
