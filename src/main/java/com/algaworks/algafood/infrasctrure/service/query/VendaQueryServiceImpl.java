package com.algaworks.algafood.infrasctrure.service.query;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.model.enums.StatusPedido;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository		//@Repository para o Spring traduzir algumas exceções para exceções de persistência
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager manager;

	/*Query do método Criteria
		select date (convert_tz(p.data_criacao, '+00:00', '-03:00')) as data_criacao,
		COUNT(p.id) as total_vendas,
		SUM(p.valor_total) as total_faturado
		FROM pedido p
		group by date(p.data_criacao)*/
	@Override
	public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<VendaDiariaDTO> query = builder.createQuery(VendaDiariaDTO.class);		//Especifica o tipo de retorno da consulta criteria
		Root<Pedido> root = query.from(Pedido.class);		//Especifica a cláusula FROM (pedido p)

		Expression<Date> functionConvertTzDataCriacao = builder.function(
				"convert_tz", Date.class, root.get("dataCriacao"),
				builder.literal("+00:00"), builder.literal(timeOffset));		//offSet "+00:00" é UTC

		//function cria uma expressão para executar uma função do BD. Primeiro param é o nome da expressão; o segundo é o tipo de dado esperado; o terceiro é o argumento da função (coluna "dataCriacao")
		Expression<Date> functionDateDataCriacao = builder.function("date", Date.class, functionConvertTzDataCriacao);

		/*Cria a seleção correspondente a um construtor. O resultado da pesquisa será usado para chamar o construtor de uma classe. Constrói VendaDiariaDTO a partir da seleção.
		Transforma cada linha de resultado do select em VendaDiariaDTO*/
		CompoundSelection<VendaDiariaDTO> selection = builder.construct(VendaDiariaDTO.class,
				functionDateDataCriacao,
				builder.count(root.get("id")),		//Faz count no ID do pedido
				builder.sum(root.get("valorTotal")));

		ArrayList<Predicate> predicates = buildPredicates(filtro, builder, root);

		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
	}

	private ArrayList<Predicate> buildPredicates(VendaDiariaFilter filtro, CriteriaBuilder builder, Root<Pedido> root){
		ArrayList<Predicate> predicates = new ArrayList<>();

		if (filtro.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}

		if (filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
		}

		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
		}

		predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));			//WHERE p.status IN ('CONFIRMADO', 'ENTREGUE')
		return predicates;
	}
}
