package com.algaworks.algafood.core.security.authorizationserver;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtKeyStoreProperties jwtKeyStoreProperties;

	@Autowired
	private DataSource dataSource;

	private static final String KEY_ID = "algafood-key-id";

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}

//	@Override		//Clients em memória
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients
//			.inMemory()
//				.withClient("algafood-web")
//				.secret(passwordEncoder.encode("web123"))
//				.authorizedGrantTypes("password", "refresh_token")
//				.scopes("write", "read")
//				.accessTokenValiditySeconds(6 * 60 * 60)	//Especifica o tempo de expiração do token - 6 horas
//				.refreshTokenValiditySeconds(60 * 24 * 60 * 60) // 60 dias
//
//			.and()
//				.withClient("foodanalytics")
//				.secret(passwordEncoder.encode(""))
//				.authorizedGrantTypes("authorization_code")
//				.scopes("write", "read")
//				.redirectUris("http://www.foodanalytics.local:8082")
//
//			.and()
//				.withClient("webadmin")
//				.authorizedGrantTypes("implicit")
//				.scopes("write", "read")
//				.redirectUris("http://aplicacao-cliente")
//
//			.and()
//				.withClient("faturamento")
//				.secret(passwordEncoder.encode("faturamento123"))
//				.authorizedGrantTypes("client_credentials")
//				.scopes("write", "read")
//
//			.and()
//				.withClient("checktoken")
//					.secret(passwordEncoder.encode("check123"));
//	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security.checkTokenAccess("isAuthenticated()");
		security.checkTokenAccess("permitAll()")
				.tokenKeyAccess("permitAll()")			//Permite chave assimétrica
			.allowFormAuthenticationForClients();
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));

		endpoints
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService)
				.authorizationCodeServices(new JdbcAuthorizationCodeServices(this.dataSource))
			.reuseRefreshTokens(false)
				.accessTokenConverter(jwtAccessTokenConverter())
				.tokenEnhancer(enhancerChain)
				.approvalStore(approvalStore(endpoints.getTokenStore()))
			.tokenGranter(tokenGranter(endpoints));
	}

	private ApprovalStore approvalStore(TokenStore tokenStore){
		TokenApprovalStore approvalStore = new TokenApprovalStore();		//Permite aprovação granular dos escopos
		approvalStore.setTokenStore(tokenStore);
		return approvalStore;
	}

	@Bean
	public JWKSet jwkSet() {
		RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
				.keyUse(KeyUse.SIGNATURE)  			//SIGNATURE pq a chave é do tipo de assinatura
				.algorithm(JWSAlgorithm.RS256)
				.keyID(KEY_ID);

		return new JWKSet(builder.build());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		var jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setKeyPair(keyPair());

		return jwtAccessTokenConverter;
	}

	private KeyPair keyPair() {
		var keyStorePass = jwtKeyStoreProperties.getPassword();
		var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();

		var keyStoreKeyFactory = new KeyStoreKeyFactory(
				jwtKeyStoreProperties.getJksLocation(), keyStorePass.toCharArray());

		return keyStoreKeyFactory.getKeyPair(keyPairAlias);
	}
	
	private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());
		
		var granters = Arrays.asList(
				pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());
		
		return new CompositeTokenGranter(granters);
	}
}
