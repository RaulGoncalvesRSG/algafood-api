package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.PedidoDTOAssembler;
import com.algaworks.algafood.api.converter.PedidoRequestDTODisassembler;
import com.algaworks.algafood.api.converter.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.dto.request.PedidoRequestDTO;
import com.algaworks.algafood.api.dto.response.PedidoDTO;
import com.algaworks.algafood.api.dto.response.PedidoResumoDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final EmissaoPedidoService emissaoPedidoService;
    private final PedidoDTOAssembler pedidoDTOAssembler;
    private final PedidoRequestDTODisassembler pedidoRequestDTODisassembler;
    private final PedidoResumoDTOAssembler pedidoResumoDTOAssembler;

    @GetMapping
    public ResponseEntity<Page<PedidoResumoDTO>> pesquisar(PedidoFilter filtro, //Apenas em add o parâmetro PedidoFilter, a pesquisa será feita sem anotação
                                           @PageableDefault(size = 10) Pageable pageable) {
        Page<Pedido> pedidos = emissaoPedidoService.listar(filtro, pageable);
        List<PedidoResumoDTO> dtos = pedidoResumoDTOAssembler.toCollectionDTO(pedidos.getContent());
        Page<PedidoResumoDTO> pedidosPage = new PageImpl<>(dtos, pageable, pedidos.getTotalElements());

        return ResponseEntity.ok(pedidosPage);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PedidoDTO> buscar(@PathVariable String codigo) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        PedidoDTO dto = pedidoDTOAssembler.toDTO(pedido);
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
}
