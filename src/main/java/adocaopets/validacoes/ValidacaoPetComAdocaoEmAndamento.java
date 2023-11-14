package adocaopets.validacoes;

import adocaopets.dtos.adocao.SolicitacaoAdocaoDto;
import adocaopets.exceptions.ValidacaoException;
import adocaopets.models.enums.StatusAdocao;
import adocaopets.repositories.AdocaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidacaoPetComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao {

    private final AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        boolean petTemAdocaoEmAndamento = adocaoRepository.
                existsByPetIdAndStatus(solicitacaoAdocaoDto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO);

            if (petTemAdocaoEmAndamento) {
                throw new ValidacaoException("Pet está aguardando uma avaliação para ser adotado!");
        }
    }
}
