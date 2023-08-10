package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.PedidoDTOAssembler;
import com.algaworks.algafood.api.v1.converter.PedidoRequestDTODisassembler;
import com.algaworks.algafood.api.v1.converter.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.v1.dto.request.PedidoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.PedidoDTO;
import com.algaworks.algafood.api.v1.dto.response.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    private final EmissaoPedidoService emissaoPedidoService;
    private final PedidoDTOAssembler pedidoDTOAssembler;
    private final PedidoRequestDTODisassembler pedidoRequestDTODisassembler;
    private final PedidoResumoDTOAssembler pedidoResumoDTOAssembler;
    private final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @GetMapping     //Apenas em add o parâmetro PedidoFilter, a pesquisa será feita sem anotação
    public ResponseEntity<PagedModel<PedidoResumoDTO>> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
        Pageable pageableTraduzido = traduzirPageable(pageable);
        Page<Pedido> pedidosPage = emissaoPedidoService.listar(filtro, pageableTraduzido);

        pedidosPage = new PageImpl<>(pedidosPage.getContent(), pageable, pedidosPage.getTotalElements());
        PagedModel<PedidoResumoDTO> pedidosPagedModel = pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoDTOAssembler);

        return ResponseEntity.ok(pedidosPagedModel);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PedidoDTO> buscar(@PathVariable String codigo) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        PedidoDTO dto = pedidoDTOAssembler.toModel(pedido);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> adicionar(@Valid @RequestBody PedidoRequestDTO pedidoInput) {
        try {
            Pedido novoPedido = pedidoRequestDTODisassembler.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedidoService.emitir(novoPedido);
            PedidoDTO dto = pedidoDTOAssembler.toDTO(novoPedido);

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable pageable) {        //Campos que podem ser ordenados. Traduz as propriedades para o nome de destino (value)
        //A key é domínio (nome da propriedade) e o value é a representação. Ex: poderia ser "nomeRestaurante" (key) e apresentar como "restaurante.nome" (value)
        Map<String, String> mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "restauranteNome", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );
        return PageableTranslator.translate(pageable, mapeamento);      //Não pode usar o memso Pageable, é precisa traduzir/converter para um novo Pageable
    }
}
