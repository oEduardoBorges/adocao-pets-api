package adocaopets.services;

import adocaopets.dtos.adocao.AprovacaoAdocaoDto;
import adocaopets.dtos.adocao.ReprovacaoAdocaoDto;
import adocaopets.dtos.adocao.SolicitacaoAdocaoDto;
import adocaopets.models.Adocao;
import adocaopets.models.Pet;
import adocaopets.models.Tutor;
import adocaopets.repositories.AdocaoRepository;
import adocaopets.repositories.PetRepository;
import adocaopets.repositories.TutorRepository;
import adocaopets.validacoes.ValidacaoSolicitacaoAdocao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdocaoService {

    private final AdocaoRepository adocaoRepository;

    private final PetRepository petRepository;

    private final TutorRepository tutorRepository;

    private final EmailService emailService;

    private final List<ValidacaoSolicitacaoAdocao> validacoes;

    public void solicitar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        Pet pet = petRepository.getReferenceById(solicitacaoAdocaoDto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor());

        validacoes.forEach(validador -> validador.validar(solicitacaoAdocaoDto));

        Adocao adocao = new Adocao(tutor, pet, solicitacaoAdocaoDto.motivo());

        adocaoRepository.save(adocao);

        emailService.enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " +adocao.getPet().getAbrigo().getNome() +
                        "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +
                        adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação."
        );
    }

    public void aprovar(AprovacaoAdocaoDto aprovacaoAdocaoDto) {
        Adocao adocao = adocaoRepository.getReferenceById(aprovacaoAdocaoDto.idAdocao());
        adocao.marcarComAprovada();
        adocaoRepository.save(adocao);

        emailService.enviarEmail(
                adocao.getTutor().getEmail(),
                "Adoção aprovada",
                "Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +
                        ", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +
                        " para agendar a busca do seu pet."
        );

    }

    public void reprovar(ReprovacaoAdocaoDto reprovacaoAdocaoDto) {
        Adocao adocao = adocaoRepository.getReferenceById(reprovacaoAdocaoDto.idAdocao());
        adocao.marcarComoReprovada(reprovacaoAdocaoDto.justificativa());

        adocaoRepository.save(adocao);

        emailService.enviarEmail(
                adocao.getTutor().getEmail(),
                "Adoção reprovada",
                "Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet " +
                        adocao.getPet().getNome() +", solicitada em " +
                        adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +
                        " com a seguinte justificativa: " +adocao.getJustificativaStatus()
        );
    }
}
