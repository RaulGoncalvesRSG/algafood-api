package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Autowired
//    private ApiRetirementHandler apiRetirementHandler;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")          //Qualquer URI do projeto q for chamada, o Spring irá habilitar o Cors
//                .allowedMethods("*");      //Define q todos métodos são acessíveis (GET, PUT, POST, etc)
////			.allowedOrigins("*")        //Por padrão aceita qualquer origin
////			.maxAge(30);
//    }

//    @Override       //Método para versionamento de API utilizando MediaType
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        //Especifica o MediaType padrão caso não seja especificado no header
//        configurer.defaultContentType(AlgaMediaTypes.V2_APPLICATION_JSON);
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(apiRetirementHandler);
//    }

    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
