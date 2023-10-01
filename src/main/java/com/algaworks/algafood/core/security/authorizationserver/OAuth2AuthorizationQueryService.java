package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.List;

public interface OAuth2AuthorizationQueryService {

    //principalName é o email (ex: joao.ger@algafood.com.br); clientId é o client da aplicação (ex: algafood-web)
    List<RegisteredClient> listClientsWithConsent(String principalName);
    List<OAuth2Authorization> listAuthorizations(String principalName, String clientId);

}
