package adocaopets.dtos.adocao;

import jakarta.validation.constraints.NotNull;

public record AprovacaoAdocaoDto(

        @NotNull
        Long idAdocao
){}
