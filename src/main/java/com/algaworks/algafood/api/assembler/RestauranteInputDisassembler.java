package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RestauranteInputDisassembler {

    private final ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInputDTO dto){
        return modelMapper.map(dto, Restaurante.class);
    }

    //N instancia um novo restaurante, usa o Restaurante do parâmetro. Copia o DTO para o domain
    public void copyToDomainObject(RestauranteInputDTO dto, Restaurante restaurante) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());      //Instância limpa de Cozinha sem nd de JPA
        modelMapper.map(dto, restaurante);
    }
}
