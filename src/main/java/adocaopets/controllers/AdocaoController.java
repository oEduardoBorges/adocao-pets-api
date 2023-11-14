package adocaopets.controllers;

import adocaopets.dtos.AprovacaoAdocaoDto;
import adocaopets.dtos.ReprovacaoAdocaoDto;
import adocaopets.dtos.SolicitacaoAdocaoDto;
import adocaopets.models.Adocao;
import adocaopets.models.enums.StatusAdocao;
import adocaopets.repositories.AdocaoRepository;
import adocaopets.services.AdocaoService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private final AdocaoService adocaoService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> solicitar(@RequestBody @Valid SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        try {
            this.adocaoService.solicitar(solicitacaoAdocaoDto);
            return ResponseEntity.status(HttpStatus.OK).body("Adoção solicitada com sucesso!");
        } catch(ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid AprovacaoAdocaoDto aprovacaoAdocaoDtoto) {
        this.adocaoService.aprovar(aprovacaoAdocaoDtoto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid ReprovacaoAdocaoDto reprovacaoAdocaoDtoo) {
        this.adocaoService.reprovar(reprovacaoAdocaoDtoo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
