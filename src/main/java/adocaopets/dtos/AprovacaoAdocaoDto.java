package adocaopets.dtos;

import jakarta.validation.constraints.NotNull;

public record AprovacaoAdocaoDto(

        @NotNull
        Long idAdocao
){}
