package adocaopets.dtos.abrigo;

import adocaopets.models.Abrigo;

public record AbrigoDto(

        Long id,

        String nome
) {

    public AbrigoDto(Abrigo abrigo) {
        this(abrigo.getId(), abrigo.getNome());
    }

    public AbrigoDto {
        if(nome != null) nome = nome.trim();
    }
}
