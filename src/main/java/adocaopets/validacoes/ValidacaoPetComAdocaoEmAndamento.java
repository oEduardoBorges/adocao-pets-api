package adocaopets.validacoes;

import adocaopets.dtos.SolicitacaoAdocaoDto;
import adocaopets.exceptions.ValidacaoException;
import adocaopets.models.Adocao;
import adocaopets.models.Pet;
import adocaopets.models.enums.StatusAdocao;
import adocaopets.repositories.AdocaoRepository;
import adocaopets.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidacaoPetComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao {

    private final AdocaoRepository adocaoRepository;

    private final PetRepository petRepository;

    public void validar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        List<Adocao> adocoes = adocaoRepository.findAll();
        Pet pet = petRepository.getReferenceById(solicitacaoAdocaoDto.idPet());

        for (Adocao a : adocoes) {
            if (a.getPet() == pet && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                throw new ValidacaoException("Pet está aguardando uma avaliação para ser adotado!");
            }
        }
    }
}
