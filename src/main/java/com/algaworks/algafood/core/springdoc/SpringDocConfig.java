package com.algaworks.algafood.core.springdoc;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.util.Constants;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
//Configurações de autorização do swagger
@SecurityScheme(
        name = "security_auth",     //Nome do SecurityScheme
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                //O nome do authorizationUrl pode ser qualquer um. URL onde pede o acessCode para ser direcionado para o login
                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
                //Gera o token
                tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
                scopes = {
                        @OAuthScope(name = "READ", description = "read scope"),
                        @OAuthScope(name = "WRITE", description = "write scope")
                }
        )))
public class SpringDocConfig {

    private static final String badRequestResponse = "BadRequestResponse";
    private static final String notFoundResponse = "NotFoundResponse";
    private static final String notAcceptableResponse = "NotAcceptableResponse";
    private static final String internalServerErrorResponse = "InternalServerErrorResponse";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AlgaFood API")
                        .version("v1")
                        .description("REST API do AlgaFood")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.com")
                        )
                ).externalDocs(new ExternalDocumentation()
                        .description("AlgaWorks")
                        .url("https://algaworks.com")
                ).tags(Arrays.asList(
                        new Tag().name(Constants.TAG_CIDADE).description(Constants.TAG_CIDADE_DESCRIPTION),
                        new Tag().name(Constants.TAG_GRUPO).description(Constants.TAG_GRUPO_DESCRIPTION),
                        new Tag().name(Constants.TAG_COZINHA).description(Constants.TAG_COZINHA_DESCRIPTION),
                        new Tag().name(Constants.TAG_FORMA_PAGAMENTO).description(Constants.TAG_FORMA_PAGAMENTO_DESCRIPTION),
                        new Tag().name(Constants.TAG_PEDIDO).description(Constants.TAG_PEDIDO_DESCRIPTION),
                        new Tag().name(Constants.TAG_RESTAURANTE).description(Constants.TAG_RESTAURANTE_DESCRIPTION),
                        new Tag().name(Constants.TAG_ESTADO).description(Constants.TAG_ESTADO_DESCRIPTION),
                        new Tag().name(Constants.TAG_PRODUTO).description(Constants.TAG_PRODUTO_DESCRIPTION),
                        new Tag().name(Constants.TAG_USUARIO).description(Constants.TAG_USUARIO_DESCRIPTION),
                        new Tag().name(Constants.TAG_PERMISSAO).description(Constants.TAG_PERMISSAO_DESCRIPTION),
                        new Tag().name(Constants.TAG_ESTATISTICA).description(Constants.TAG_ESTATISTICA_DESCRIPTION)
                )).components(new Components()
                        .schemas(gerarSchemas())
                        .responses(gerarResponses())
                );
    }

    @Bean       //Adiciona descrição para um determinado status code de forma global
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            openApi.getPaths()
                    .values()
                    .forEach(pathItem -> pathItem.readOperationsMap()
                            .forEach((httpMethod, operation) -> {
                                ApiResponses responses = operation.getResponses();
                                //OBS: O description utilizado de forma global para um determinado status code sobreescreve o description no OpenApi do endpoint
                                switch (httpMethod) {
                                    case GET:
                                        //A referência $ref pega o description do ApiResponse (método gerarResponses)
                                        responses.addApiResponse("406", new ApiResponse().$ref(notAcceptableResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    case POST:
                                        responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    case PUT:
                                        responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    case DELETE:
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    default:
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                }
                            })
                    );
        };
    }

    //Apenas criar os schemas n é o suficiente para fazer aparecer no swagger, é preciso utilizá-lo em algum endpoint
    private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(Problem.class);
        Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(Problem.Field.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);

        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        //Content para ser referenciado o modelo de representação de problema (obj Problem) com código de status de erro
        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<Problem>().$ref(Constants.SCHEMA_PROBLEMA)));

        apiResponseMap.put(badRequestResponse, new ApiResponse()
                .description("Requisição inválida")
                .content(content));

        apiResponseMap.put(notFoundResponse, new ApiResponse()
                .description("Recurso não encontrado")
                .content(content));

        //O método openApiCustomiser utiliza notAcceptableResponse no code 406, então o description e content (obj Problem) serão apresentados no swagger
        apiResponseMap.put(notAcceptableResponse, new ApiResponse()
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .content(content));

        apiResponseMap.put(internalServerErrorResponse, new ApiResponse()
                .description("Erro interno no servidor")
                .content(content));

        return apiResponseMap;
    }

}
