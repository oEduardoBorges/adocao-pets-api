package adocaopets.validacoes;

import adocaopets.dtos.SolicitacaoAdocaoDto;
import adocaopets.exceptions.ValidacaoException;
import adocaopets.models.Adocao;
import adocaopets.models.Pet;
import adocaopets.models.Tutor;
import adocaopets.models.enums.StatusAdocao;
import adocaopets.repositories.AdocaoRepository;
import adocaopets.repositories.PetRepository;
import adocaopets.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidacaoTutorComLimiteDeAdocoes implements ValidacaoSolicitacaoAdocao {

    private final AdocaoRepository adocaoRepository;

    private final TutorRepository tutorRepository;

    public void validar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        List<Adocao> adocoes = adocaoRepository.findAll();
        Tutor tutor = tutorRepository.getReferenceById(solicitacaoAdocaoDto.idPet());

        for (Adocao a : adocoes) {
            int contador = 0;
            if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.APROVADO) {
                contador = contador + 1;
            }
            if (contador == 5) {
                throw new ValidacaoException("Tutor chegou ao limite máximo de adoções: 5");
            }
        }
    }
}
