package adocaopets.services;

import adocaopets.dtos.pet.CadastroPetDto;
import adocaopets.dtos.pet.PetDto;
import adocaopets.models.Abrigo;
import adocaopets.models.Pet;
import adocaopets.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public Page<PetDto> buscarPetsDisponiveis(Pageable pageable) {
        return petRepository
                .findAllByAdotadoFalse(pageable)
                .map(PetDto::new);
    }

    public void cadastrarPet(Abrigo abrigo, CadastroPetDto cadastroPetDto) {
        petRepository.save(new Pet(cadastroPetDto, abrigo));
    }
}
