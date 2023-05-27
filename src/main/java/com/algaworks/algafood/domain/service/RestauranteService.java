package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RestauranteService {

    private final RestauranteRepository repository;
    private final CozinhaService cozinhaService;
    private final CidadeService cidadeService;
    private final FormaPagamentoService formaPagamentoService;

    private static final String FORMA_PAGAMENTO_NAO_ASSOCIADA = "A forma de pagamento do código %d não esta associado ao restaurante";

    public List<Restaurante> listar(){
        return repository.findAll();
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return repository.save(restaurante);
    }

    public Restaurante buscarOuFalhar(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public void ativar(Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        /*N precisa usar o epository.save pq qnd busca o Restaurante com repository.findById, a instância do Restaurante fica em um estado gerenciável pelo contexto de persistência do JPA
        Qualquer modificação feita nesse obj dentro dessa transação será sincronizada (fazer update) com BD, o JPA conseguen entender isso*/
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.inativar();
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);

        if (restaurante.formaPagamentoNaoAssociada(formaPagamentoId)) {
            FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
            restaurante.adicionarFormaPagamento(formaPagamento);
        }
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Optional<FormaPagamento> formaPagamentoOpt = restaurante.encontrarFormaPagamento(formaPagamentoId);

        if (formaPagamentoOpt.isEmpty()){
            throw new NegocioException(String.format(FORMA_PAGAMENTO_NAO_ASSOCIADA, formaPagamentoId));
        }
        restaurante.removerFormaPagamento(formaPagamentoOpt.get());
    }

    @Transactional
    public void abrir(Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.fechar();
    }
}
