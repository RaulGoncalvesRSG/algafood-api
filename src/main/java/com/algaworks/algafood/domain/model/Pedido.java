package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@FieldNameConstants         //Pega a String com o nome da variável. Se refatorar o nome da propriedade, a constante na spec factory também vai ser refatorada
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)       //Anotação para n usar EqualsAndHashCode da classe extendida, mas sim a de Pedido
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)  //Enumerated STRING para determinar como o enum será salvo no BD e qnd buscar pedido, conseguir converter a string para Enum
    private StatusPedido status = StatusPedido.CRIADO;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @ManyToOne(fetch = FetchType.LAZY)      //Faz select apenas se precisar
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)      //Qnd salvar o pedido, salva os itens em cascata
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        itens.forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItens().stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());

        registerEvent(new PedidoConfirmadoEvent(this));     //Registra o evendo q deve ser disparado assim q o obj desta entidade for salvo
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
    }

    public boolean podeSerConfirmado() {
        return getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
    }

    public boolean podeSerEntregue() {
        return getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
    }

    public boolean podeSerCancelado() {
        return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
    }

    @PrePersist     //Método callback JPA é executado em alguns eventos do ciclo de vida da entidade. Um dos eventos é o PrePersist: antes de persistir o dado no BD, executa o método
    private void gerarCodigo(){
        setCodigo(UUID.randomUUID().toString());
    }
}
