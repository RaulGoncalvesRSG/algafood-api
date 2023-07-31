package com.algaworks.algafood.core.squiggly;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
        Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null));

        //Urls que aceitam a possibilidade de passar campos por parâmetro
        List<String> urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");

        //Add no projeto um filtro nas reqyisições http. Sempre que alguma requsição chegar na API, vai passar por esse filtro
        FilterRegistrationBean<SquigglyRequestFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new SquigglyRequestFilter());
        filterRegistration.setOrder(1);
        filterRegistration.setUrlPatterns(urlPatterns);         //Informa as urls que aceitam o filtro. O padrão é aceitar todas

        return filterRegistration;
    }
}
