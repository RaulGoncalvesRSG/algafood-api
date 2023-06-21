package com.algaworks.algafood.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FotoProduto {

	@Id
	@Column(name = "produto_id")
	private Long id;

	/*MapsId informa q a propriedade produto é mapeada pelo ID da entetidade FotoProduto (produto_id).
	N usou @JoinColumn(name = "produto_id") pq a a propriedade id ja foi mapeada por produto_id, então n pode fazer outro mapeamento dessa forma*/
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Produto produto;
	
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
	public Long getRestauranteId() {
		if (getProduto() != null) {
			return getProduto().getRestaurante().getId();
		}
		return null;
	}
	
}