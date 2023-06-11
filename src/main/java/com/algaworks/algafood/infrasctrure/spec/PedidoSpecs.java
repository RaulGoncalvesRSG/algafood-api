package com.algaworks.algafood.infrasctrure.spec;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpecs {

	//Para o filtro funcionar na busca do JPA, é preciso estender o repository com JpaSpecificationExecutor
	public static Specification<Pedido> usarFiltro(PedidoFilter filtro) {
		return (root, query, builder) -> {
			//Verifica se o ResultType é do tipo Pedido. Se for, é um select para retornar Pedidos e faz o fetch. Quando usa count,o ResultType é um tipo numérico
			if (Pedido.class.equals(query.getResultType())) {
				//Para cada pedido retornado, estava fazendo um select no restaurante. Usando .fetch("restaurante"), resolve o problema do N+1 com restaurante. Isso é equivalente a usar from Pedido p join fetch p.restaurante, mas dessa vez é usando é com criteria
				root.fetch("restaurante").fetch("cozinha");			//cozinha está dentro de restaurante
				root.fetch("cliente");
			}

			ArrayList<Predicate> predicates = new ArrayList<>();

			if (filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));			//get pega o nome da propriedade em Pedido
			}
			
			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
			}
			
			if (filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), 
						filtro.getDataCriacaoInicio()));
			}
			
			if (filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), 
						filtro.getDataCriacaoFim()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
