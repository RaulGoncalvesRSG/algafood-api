package com.algaworks.algafood.api;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@UtilityClass       //@UtilityClass transforma a classe em final, uma classe que n pode ser estendida
public class ResourceUriHelper {

    public static URI generateURI(Object resourceId){
       return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resourceId).toUri();
    }
}
