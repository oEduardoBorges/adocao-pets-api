package adocaopets.dtos.tutor;

import adocaopets.models.Abrigo;
import adocaopets.models.Tutor;

public record ListarTutoresDto(

        Long id,

        String nome,

        String telefone,

        String email
){

    public ListarTutoresDto(Tutor tutor) {
        this(tutor.getId(), tutor.getNome(), tutor.getTelefone(), tutor.getEmail());
    }
}
