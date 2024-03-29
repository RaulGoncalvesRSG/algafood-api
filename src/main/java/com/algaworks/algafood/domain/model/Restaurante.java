package com.algaworks.algafood.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@FieldNameConstants
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @Builder.Default
    private Boolean ativo = Boolean.TRUE;

    @Builder.Default
    private Boolean aberto = Boolean.FALSE;

    @CreationTimestamp          //Atribui um horário para a classe no momento q ela for salva no BD
    @Column(nullable = false, columnDefinition = "datetime") //columnDefinition sem precisão de ms
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp            //Atribui um horário para a classe sempre q ela for atualizada
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "restaurante")            //Produto é o dono da relação
    private List<Produto> produtos = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuarioId"))
    private Set<Usuario> responsaveis  = new HashSet<>();

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public void adicionarFormaPagamento(FormaPagamento formaPagamento){
        formasPagamento.add(formaPagamento);
    }

    public void removerFormaPagamento(FormaPagamento formaPagamento){
        formasPagamento.remove(formaPagamento);
    }

    public boolean formaPagamentoNaoAssociada(Long formaPagamentoId){
        return encontrarFormaPagamento(formaPagamentoId).isEmpty();
    }

    public Optional<FormaPagamento> encontrarFormaPagamento(Long formaPagamentoId) {
        return formasPagamento.stream()
                .filter(formaPagamento -> formaPagamento.getId().equals(formaPagamentoId))
                .findFirst();
    }

    public void abrir(){
        setAberto(true);
    }

    public void fechar(){
        setAberto(false);
    }

    public void adicionarResponsavel(Usuario usuario){
        responsaveis.add(usuario);
    }

    public void removerResponsavel(Usuario usuario){
        responsaveis.remove(usuario);
    }

    public boolean responsavelNaoAssociado(Long usuarioId){
        return encontrarResponsavel(usuarioId).isEmpty();
    }

    public Optional<Usuario> encontrarResponsavel(Long usuarioId) {
        return responsaveis.stream()
                .filter(usuario -> usuario.getId().equals(usuarioId))
                .findFirst();
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return formasPagamento.contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !aceitaFormaPagamento(formaPagamento);
    }
}
