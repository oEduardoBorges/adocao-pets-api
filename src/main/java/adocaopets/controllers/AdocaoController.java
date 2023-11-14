package adocaopets.controllers;

import adocaopets.models.Adocao;
import adocaopets.models.enums.StatusAdocao;
import adocaopets.repositories.AdocaoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/adocoes")
@RequiredArgsConstructor
public class AdocaoController {

    private final AdocaoRepository adocaoRepository;

    private final JavaMailSender emailSender;

    @PostMapping
    @Transactional
    public ResponseEntity<String> solicitar(@RequestBody @Valid Adocao adocao) {
        if (adocao.getPet().getAdotado() == true) {
            return ResponseEntity.badRequest().body("Pet já foi adotado!");
        } else {
            List<Adocao> adocoes = adocaoRepository.findAll();
            for (Adocao a : adocoes) {
                if (a.getTutor() == adocao.getTutor() && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    return ResponseEntity.badRequest().body("Tutor aguardando outra avaliação para adoção!");
                }
            }
            for (Adocao a : adocoes) {
                if (a.getPet() == adocao.getPet() && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    return ResponseEntity.badRequest().body("Pet está aguardando uma avaliação para ser adotado!");
                }
            }
            for (Adocao a : adocoes) {
                int contador = 0;
                if (a.getTutor() == adocao.getTutor() && a.getStatus() == StatusAdocao.APROVADO) {
                    contador = contador + 1;
                }
                if (contador == 5) {
                    return ResponseEntity.badRequest().body("Tutor chegou ao limite máximo de adoções: 5");
                }
            }
        }
        adocao.setData(LocalDateTime.now());
        adocao.setStatus(StatusAdocao.AGUARDANDO_AVALIACAO);
        adocaoRepository.save(adocao);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adocaopets@email.com");
        email.setTo(adocao.getPet().getAbrigo().getEmail());
        email.setSubject("Solicitação de adoção");
        email.setText("Olá " +adocao.getPet().getAbrigo().getNome() +
                "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +
                adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação.");
        emailSender.send(email);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid Adocao adocao) {
        adocao.setStatus(StatusAdocao.APROVADO);
        adocaoRepository.save(adocao);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adocaopets@email.com");
        email.setTo(adocao.getTutor().getEmail());
        email.setSubject("Adoção aprovada");
        email.setText("Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +
                ", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                ", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +
                " para agendar a busca do seu pet.");
        emailSender.send(email);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid Adocao adocao) {
        adocao.setStatus(StatusAdocao.REPROVADO);
        adocaoRepository.save(adocao);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adocaopets@email.com");
        email.setTo(adocao.getTutor().getEmail());
        email.setSubject("Adoção reprovada");
        email.setText("Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet " +
                adocao.getPet().getNome() +", solicitada em " +
                adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                ", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +
                " com a seguinte justificativa: " +adocao.getJustificativaStatus());
        emailSender.send(email);

        return ResponseEntity.ok().build();
    }
}
