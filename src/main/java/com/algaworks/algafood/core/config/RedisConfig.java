package com.algaworks.algafood.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

import java.util.HashMap;

@Configuration
public class RedisConfig {

    @Bean
  //  @Profile("development")
    public SessionRepository<?> sessionRepository() {
        return new MapSessionRepository(new HashMap<String, Session>());
    }
}
