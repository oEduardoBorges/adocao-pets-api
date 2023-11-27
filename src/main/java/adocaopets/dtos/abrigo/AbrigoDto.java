package adocaopets.dtos.abrigo;

import adocaopets.models.Abrigo;


public record AbrigoDto(

        Long id,

        String nome,

        String telefone,

        String email
) {

    public AbrigoDto(Abrigo abrigo) {
        this(abrigo.getId(), abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail());
    }

    public AbrigoDto {
        if(nome != null) nome = nome.trim();
    }
}
