package com.algaworks.algafood.api.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class CozinhaIdRequestDTO {

    @NotNull
    private Long id;
}
