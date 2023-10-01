package com.algaworks.algafood.infrasctrure.service.query;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.model.enums.StatusPedido;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CompoundSelection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository		//@Repository para o Spring traduzir algumas exceções para exceções de persistência
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager manager;

	/*Query do método Criteria
		SELECT date (convert_tz(p.data_criacao, '+00:00', '-03:00')) AS data_criacao,
		COUNT(p.id) AS total_vendas,
		SUM(p.valor_total) AS total_faturado
		FROM pedido p
		GROUP BY date(p.data_criacao)*/
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

		if (filtro.getRestauranteId() != null) {						//root.get("restaurante") - pega o MESMO nome do atributo da classe Java infromada no Root (Pedido)
			predicates.add(builder.equal(root.get(Pedido.Fields.restaurante).get(Pedido.Fields.id), filtro.getRestauranteId()));
		}

		if (filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Pedido.Fields.dataCriacao), filtro.getDataCriacaoInicio()));
		}

		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Pedido.Fields.dataCriacao), filtro.getDataCriacaoFim()));
		}

		predicates.add(root.get(Pedido.Fields.status).in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));			//WHERE p.status IN ('CONFIRMADO', 'ENTREGUE')
		return predicates;
	}

	@Override
	public List<VendaDiariaDTO> consultarVendasDiariasJPQL(VendaDiariaFilter filtro) {
		StringBuilder jpql = new StringBuilder(
				"SELECT new com.algaworks.algafood.domain.model.dto.VendaDiaria(" +
						"FUNCTION('date', p.dataCriacao), COUNT(p.id), SUM(p.valorTotal)) " +
						"FROM Pedido p ");

		String where = " WHERE p.status IN ('CONFIRMADO','ENTREGUE') ";
		boolean setDataCriacaoInicio = false;
		boolean setDataCriacaoFim = false;
		boolean setRestauranteId = false;

		if (filtro.getDataCriacaoInicio() != null) {
			where += " AND p.dataCriacao >= :dataCriacaoInicio ";
			setDataCriacaoInicio = true;
		}

		if (filtro.getDataCriacaoFim() != null) {
			where += " AND p.dataCriacao <= :dataCriacaoFim ";
			setDataCriacaoFim = true;
		}

		if (filtro.getRestauranteId() != null) {
			where += " AND  p.restaurante.id = :restauranteId ";
			setRestauranteId = true;
		}

		jpql.append(where);
		jpql.append("GROUP BY FUNCTION('date', p.dataCriacao)");

		TypedQuery<VendaDiariaDTO> query = manager.createQuery(jpql.toString(), VendaDiariaDTO.class);

		if (setDataCriacaoInicio) {
			query.setParameter("dataCriacaoInicio", filtro.getDataCriacaoInicio());
		}

		if (setDataCriacaoFim) {
			query.setParameter("dataCriacaoFim", filtro.getDataCriacaoFim());
		}

		if (setRestauranteId) {
			query.setParameter("restauranteId", filtro.getRestauranteId());
		}
		return query.getResultList();
	}
}
