package com.algaworks.algafood.infrasctrure.service.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
public class ProcessadorEmailTemplate {

    @Autowired
    private Configuration freemarkerConfig;

    protected String processarTemplate(EnvioEmailService.Mensagem mensagem) {
        try {
            //Faz o atributo "corpo" ser o nome do arquivo do corpo html
            Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());
            //Segundo param é um Object Java utilizado para gerar o html dinamicamente
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do e-mail", e);
        }
    }
}
