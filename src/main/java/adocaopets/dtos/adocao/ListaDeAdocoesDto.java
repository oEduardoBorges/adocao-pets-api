package adocaopets.dtos.adocao;

import adocaopets.models.Adocao;
import adocaopets.models.enums.StatusAdocao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ListaDeAdocoesDto(

        Long idAdocao,

        Long idPet,

        Long idTutor,

        String motivo,

        StatusAdocao status
) {

    public ListaDeAdocoesDto(Adocao adocao) {
        this(adocao.getId(), adocao.getPet().getId(), adocao.getTutor().getId(), adocao.getMotivo(), adocao.getStatus());
    }
}
