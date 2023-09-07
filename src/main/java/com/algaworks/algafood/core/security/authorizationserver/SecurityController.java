package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller		//Essa clase n é para endpoont Rest, apaenas para direcionar para página de login
public class SecurityController {

	@GetMapping("/login")		//Mesmo caminho do formLogin().loginPage do ResourceServerConfig
	public String login() {
		return "pages/login";
	}
	
	@GetMapping("/oauth/confirm_access")
	public String approval() {
		return "pages/approval";
	}
	
}
