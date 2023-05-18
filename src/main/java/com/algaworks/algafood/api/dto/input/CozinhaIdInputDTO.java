package com.algaworks.algafood.api.dto.input;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class CozinhaIdInputDTO {

    @NotNull
    private Long id;
}
