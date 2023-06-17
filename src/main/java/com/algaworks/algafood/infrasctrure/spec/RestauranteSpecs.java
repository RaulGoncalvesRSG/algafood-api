package com.algaworks.algafood.infrasctrure.spec;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestauranteSpecs  {

    public static Specification<Restaurante> comFreteGratis(){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Restaurante.Fields.taxaFrete), BigDecimal.ZERO);  //Sem filtro dinãmico
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Restaurante.Fields.nome), "%" + nome + "%");
    }
}
