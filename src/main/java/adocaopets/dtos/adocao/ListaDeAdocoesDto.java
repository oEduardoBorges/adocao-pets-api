package adocaopets.dtos.adocao;

import adocaopets.models.Adocao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ListaDeAdocoesDto(

        Long idAdocao,

        @NotNull
        Long idPet,

        @NotNull
        Long idTutor,

        @NotBlank
        String motivo
) {

    public ListaDeAdocoesDto(Adocao adocao) {
        this(adocao.getId(), adocao.getPet().getId(), adocao.getTutor().getId(), adocao.getMotivo());
    }
}
