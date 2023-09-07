package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
//EnableGlobalMethodSecurity habita restrição de acesso nos métodos. prePostEnabled = true para @PreAuthorize funcionar
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	private static final String LOGIN_PAGE = "/login";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.formLogin().loginPage(LOGIN_PAGE)		//Pode ser qualquer URL
				.and()
				//Precisa estar autenticado para fazer uma requisição que inicia com "",
				.authorizeRequests()
					.antMatchers("/oauth/**").authenticated()
				.and()
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
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			List<String> authorities = jwt.getClaimAsStringList("authorities");

			if (Objects.isNull(authorities)) {
				authorities = Collections.emptyList();
			}

			JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
			//Coleção com apenas autorizações dos escopos do Client
			Collection<GrantedAuthority> grantedAuthorities = authoritiesConverter.convert(jwt);

			//Autorizações do usuário logado
			List<SimpleGrantedAuthority> collect = authorities.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());

			grantedAuthorities.addAll(collect);

			return grantedAuthorities;
		});
		return jwtAuthenticationConverter;
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

//	@Bean		//Chave secreta simétrica
//	public JwtDecoder jwtDecoder() {
//		//1 arg é um array de bytes e o 2 arg é o algoritmo para a chave secreta
//		var secretKey = new SecretKeySpec("89a7sd89f7as98f7dsa98fds7fd89sasd9898asdf98s".getBytes(), "HmacSHA256");
//		return NimbusJwtDecoder.withSecretKey(secretKey).build();
//	}
}
