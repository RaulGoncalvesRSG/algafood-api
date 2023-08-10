package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.v1.dto.response.CidadeDTO;
import com.algaworks.algafood.api.v1.dto.response.CozinhaDTO;
import com.algaworks.algafood.api.v1.dto.response.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.dto.response.RestauranteBasicoDTO;
import com.algaworks.algafood.api.v1.dto.response.UsuarioDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.openapi.model.CidadesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.RestaurantesBasicoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.UsuariosModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@EnableSwagger2
//Configuração do Spring Fox para reconehcer o bean validation e associar ao swagger
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .select()
                    //Escane apenas controladores do pacote especificado para gerar o JSON
                    .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                    //paths filtra os caminhos. Ex: PathSelectors.ant("/restaurantes/*)
                    .paths(PathSelectors.any())
                    .build()
                //Desabilita os códigos de status que aparecem automaticamente (401, 403 e 404)
                .useDefaultResponseMessages(false)
                //globalResponses descreve configurações de status code padrão de forma global (tds endpoints do projeto) para um determinado verbo HTTP
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                //Adicionando a classe Problema com TypeResolver
                .additionalModels(typeResolver.resolve(Problem.class))
                //Ignora os parâmetros dos endppoints de determinados tipos para não aparecer no swagger
                .ignoredParameterTypes(getClasses())
                //Classe original (1 param) e classe que irá subsituir (2 param). Substituição apenas para documentação
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)

                //Regra de substituição. Subistiui Page<PedidoResumoDTO> por PedidosResumoModelOpenApi. Caso precise em outra classe, é preciso usar um alternateTypeRules para cada casoz
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, PedidoResumoDTO.class), PedidosResumoModelOpenApi.class))

                //Substitui a documentação do endpoint q retorna CozinhaDTO pela documentação de CozinhasModelOpenApi
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaDTO.class),
                        CozinhasModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeDTO.class),
                        CidadesModelOpenApi.class))

//                .alternateTypeRules(AlternateTypeRules.newRule(
//                        typeResolver.resolve(CollectionModel.class, EstadoDTO.class),
//                        EstadosModelOpenApi.class))
//
//                .alternateTypeRules(AlternateTypeRules.newRule(
//                        typeResolver.resolve(CollectionModel.class, FormaPagamentoDTO.class),
//                        FormasPagamentoModelOpenApi.class))
//
//                .alternateTypeRules(AlternateTypeRules.newRule(
//                        typeResolver.resolve(CollectionModel.class, GrupoDTO.class),
//                        GruposModelOpenApi.class))

//                .alternateTypeRules(AlternateTypeRules.newRule(
//                        typeResolver.resolve(CollectionModel.class, PermissaoDTO.class),
//                        PermissoesModelOpenApi.class))
//
//                .alternateTypeRules(AlternateTypeRules.newRule(
//                        typeResolver.resolve(CollectionModel.class, ProdutoDTO.class),
//                        ProdutosModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestauranteBasicoDTO.class),
                        RestaurantesBasicoModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UsuarioDTO.class),
                        UsuariosModelOpenApi.class))

                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"),
                        new Tag("Estados", "Gerencia os estados"),
                        new Tag("Produtos", "Gerencia os produtos de restaurantes"),
                        new Tag("Usuários", "Gerencia os usuários"),
                        new Tag("Estatísticas", "Estatísticas da AlgaFood"));
    }

    private Class getClasses(){
        return Arrays.asList(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, Resource.class, File.class, InputStream.class).getClass();
    }

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }

    //Se um controlador estiver alguma configuração para um determinado status code, ele substitui a configuração padrão
    private List<Response> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build()
        );
    }

    private List<Response> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno no servidor")
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .build()
        );
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno no servidor")
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .build()
        );
    }

    private Consumer<RepresentationBuilder> getProblemaModelReference() {
        /*O parâmetro "Problema" é a @ApiModel da classe Problem. Isso está fazendo referenciar o cod da resposta com o Problem.
        Se oorrer erro 400, vai aparecer o body da classe Problem na documentação*/
        return r -> r.model(m -> m.name("Problema")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
                        q -> q.name("Problema").namespace("com.algaworks.algafood.api.exceptionhandler")))));
    }

    //Informações do Swagger UI
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
                .build();
    }
}
