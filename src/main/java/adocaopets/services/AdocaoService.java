package adocaopets.services;

import adocaopets.dtos.AprovacaoAdocaoDto;
import adocaopets.dtos.ReprovacaoAdocaoDto;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdocaoService {

    private final AdocaoRepository adocaoRepository;

    private final PetRepository petRepository;

    private final TutorRepository tutorRepository;

    private final EmailService emailService;

    public void solicitar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        Pet pet = petRepository.getReferenceById(solicitacaoAdocaoDto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor());

        if (pet.getAdotado() == true) {
            throw new ValidacaoException("Pet já foi adotado!");
        } else {
            List<Adocao> adocoes = adocaoRepository.findAll();
            for (Adocao a : adocoes) {
                if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    throw new ValidacaoException("Tutor aguardando outra avaliação para adoção!");
                }
            }
            for (Adocao a : adocoes) {
                if (a.getPet() == pet && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    throw new ValidacaoException("Pet está aguardando uma avaliação para ser adotado!");
                }
            }
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

        Adocao adocao = new Adocao();
        adocao.setData(LocalDateTime.now());
        adocao.setStatus(StatusAdocao.AGUARDANDO_AVALIACAO);
        adocao.setPet(pet);
        adocao.setTutor(tutor);
        adocao.setMotivo(solicitacaoAdocaoDto.motivo());
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
        adocao.setStatus(StatusAdocao.APROVADO);
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
        adocao.setStatus(StatusAdocao.REPROVADO);
        adocao.setJustificativaStatus(reprovacaoAdocaoDto.justificativa());
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
