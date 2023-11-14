package adocaopets.dtos.adocao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReprovacaoAdocaoDto(

        @NotNull
        Long idAdocao,

        @NotBlank
        String justificativa
){}
