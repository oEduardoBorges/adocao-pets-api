package adocaopets.dtos.pet;

import adocaopets.models.Pet;
import adocaopets.models.enums.TipoPet;

public record PetDto(

        Long id,

        TipoPet tipo,

        String nome,

        String raca,

        Integer idade
) {

    public PetDto(Pet pet) {
        this(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade());
    }

    public PetDto {
        if(nome != null) nome = nome.trim();
    }
}
