package com.algaworks.algafood.core.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component      //Apenas registrar como componente não resolve, tbm é preciso usar InterceptorRegistry no WenConfig
public class ApiRetirementHandler implements HandlerInterceptor {  //Interceptador dos métodos dos controladores

    private static final String HEADER_KEY = "X-AlgaFood-Deprecated";
    private static final String HEADER_VALUE = "Essa versão da API está depreciada e deixará de existir a partir de 01/01/2025. Use a versão mais atual da API.";

    @Override   //Este método é chamado (intercpita) antes do méodo do controlador ser chamado
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //request.getRequestURI() pega o caminha da requisição
        if (request.getRequestURI().startsWith("/v1/")) {
         //   response.addHeader(HEADER_KEY, HEADER_VALUE);       //Add cabeçalho para todas URLs que começam com "/v1/
            response.setStatus(HttpStatus.GONE.value());           //Recurso não existe mais no sistema
            return false;                   //Se retornar false, interrompe a execução do método
        }
        return true;
    }
}
