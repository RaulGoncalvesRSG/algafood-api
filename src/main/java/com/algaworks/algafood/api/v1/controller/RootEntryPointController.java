package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class RootEntryPointController {

    private final AlgaLinks algaLinks;

    private static final String COZINHAS = "cozinhas";
    private static final String PEDIDOS = "pedidos";
    private static final String RESTAURANTES = "restaurantes";
    private static final String GRUPOS = "grupos";
    private static final String USUARIOS = "usuarios";
    private static final String PERMISSOES = "permissoes";
    private static final String FORMAS_PAGAMENTO = "formas-pagamento";
    private static final String ESTADOS = "estados";
    private static final String CIDADES = "cidades";
    private static final String ESTATISTICAS = "estatisticas";

    @GetMapping     //Retorna os links do projeto como se fosse um menu principal
    public RootEntryPointModel root() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(algaLinks.linkToCozinhas(COZINHAS));
        rootEntryPointModel.add(algaLinks.linkToPedidos(PEDIDOS));
        rootEntryPointModel.add(algaLinks.linkToRestaurantes(RESTAURANTES));
        rootEntryPointModel.add(algaLinks.linkToGrupos(GRUPOS));
        rootEntryPointModel.add(algaLinks.linkToUsuarios(USUARIOS));
        rootEntryPointModel.add(algaLinks.linkToPermissoes(PERMISSOES));
        rootEntryPointModel.add(algaLinks.linkToFormasPagamento(FORMAS_PAGAMENTO));
        rootEntryPointModel.add(algaLinks.linkToEstados(ESTADOS));
        rootEntryPointModel.add(algaLinks.linkToCidades(CIDADES));
        rootEntryPointModel.add(algaLinks.linkToEstatisticas(ESTATISTICAS));

        return rootEntryPointModel;
    }

    //Classe para poder add os links
    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }
}
