package adocaopets.validacoes;

import adocaopets.dtos.SolicitacaoAdocaoDto;
import adocaopets.exceptions.ValidacaoException;
import adocaopets.models.Pet;
import adocaopets.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidacaoPetDisponivel implements ValidacaoSolicitacaoAdocao {

    private final PetRepository petRepository;

    public void validar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        Pet pet = petRepository.getReferenceById(solicitacaoAdocaoDto.idPet());

        if (pet.getAdotado()) {
            throw new ValidacaoException("Pet já foi adotado!");
        }
    }
}
