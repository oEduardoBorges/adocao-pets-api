package adocaopets.controllers;

import adocaopets.dtos.tutor.AtualizacaoTutorDto;
import adocaopets.dtos.tutor.CadastroTutorDto;
import adocaopets.dtos.tutor.ListarTutoresDto;
import adocaopets.exceptions.ValidacaoException;
import adocaopets.services.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tutores")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    @PreAuthorize("hasAnyRole('ADMIN)")
    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroTutorDto cadastroTutorDto) {
        try {
            tutorService.cadastrar(cadastroTutorDto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<ListarTutoresDto>> listar(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(tutorService.listar(pageable));
    }

    @GetMapping("/{id}")
    public Optional<ListarTutoresDto> listarTutorPorId(@PathVariable Long id) {
        return tutorService.listarTutorPorId(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN)")
    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizacaoTutorDto atualizacaoTutorDto) {
        try {
            tutorService.atualizar(atualizacaoTutorDto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
