package adocaopets.validacoes;

import adocaopets.dtos.SolicitacaoAdocaoDto;
import adocaopets.exceptions.ValidacaoException;
import adocaopets.models.Adocao;
import adocaopets.models.Pet;
import adocaopets.models.Tutor;
import adocaopets.models.enums.StatusAdocao;
import adocaopets.repositories.AdocaoRepository;
import adocaopets.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidacaoTutorComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao {

    private final AdocaoRepository adocaoRepository;

    private final TutorRepository tutorRepository;

    public void validar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        List<Adocao> adocoes = adocaoRepository.findAll();
        Tutor tutor = tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor());

        for (Adocao a : adocoes) {
            if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                throw new ValidacaoException("Tutor aguardando outra avaliação para adoção!");
            }
        }
    }
}
