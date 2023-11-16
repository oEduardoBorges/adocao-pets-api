package adocaopets.controllers;

import adocaopets.dtos.adocao.AprovacaoAdocaoDto;
import adocaopets.dtos.adocao.ReprovacaoAdocaoDto;
import adocaopets.dtos.adocao.SolicitacaoAdocaoDto;
import adocaopets.services.AdocaoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
        try {
            this.adocaoService.aprovar(aprovacaoAdocaoDtoto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid ReprovacaoAdocaoDto reprovacaoAdocaoDtoo) {
        try {
            this.adocaoService.reprovar(reprovacaoAdocaoDtoo);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
