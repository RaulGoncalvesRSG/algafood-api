package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.dto.request.RestauranteRequestDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RestauranteRequestDTODisassembler {

    private final ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteRequestDTO dto){
        return modelMapper.map(dto, Restaurante.class);
    }

    //N instancia um novo restaurante, usa o Restaurante do parâmetro. Copia o DTO para o domain
    public void copyToDomainObject(RestauranteRequestDTO dto, Restaurante restaurante) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());      //Instância limpa de Cozinha sem nd de JPA

        if (Objects.nonNull(restaurante.getEndereco())){
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(dto, restaurante);
    }
}
