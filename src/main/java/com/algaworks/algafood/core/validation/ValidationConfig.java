package com.algaworks.algafood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration      //Classe para possibilitar o uso de mensagens do javax.validation.constraints no properties. OBS: N é necessário utilizar o javax.validation.constraints
public class ValidationConfig {

    @Bean       //LocalValidatorFactoryBean é uma classe q faz integração e configuraçãp do BeanValidation com Spring
    public LocalValidatorFactoryBean validator(MessageSource messageSource){  //É a classe responsável por ler o arquivo messages.properties
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
