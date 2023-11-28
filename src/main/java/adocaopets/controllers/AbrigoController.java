package adocaopets.controllers;

import adocaopets.dtos.abrigo.AbrigoDto;
import adocaopets.dtos.abrigo.CadastroAbrigoDto;
import adocaopets.dtos.pet.CadastroPetDto;
import adocaopets.dtos.pet.PetDto;
import adocaopets.exceptions.ValidacaoException;
import adocaopets.models.Abrigo;
import adocaopets.services.AbrigoService;
import adocaopets.services.PetService;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/abrigos")
@RequiredArgsConstructor
public class AbrigoController {

    private final AbrigoService abrigoService;

    private final PetService petService;

    @PreAuthorize("hasAnyRole('ADMIN)")
    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroAbrigoDto cadastroAbrigoDto) {
        try {
            abrigoService.cadatrar(cadastroAbrigoDto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<AbrigoDto>> listar(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<AbrigoDto> abrigos = abrigoService.listar(pageable);
        return ResponseEntity.ok(abrigos);
    }

    @GetMapping("/{id}")
    public Optional<AbrigoDto> listarAbrigoPorId(@PathVariable Long id) {
        return abrigoService.listarAbrigoPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAbrigo(@PathVariable Long id, @Valid @RequestBody CadastroAbrigoDto abrigoDto) {
        abrigoService.atualizarAbrigo(id, abrigoDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<PetDto>> listarPets(@PathVariable String idOuNome) {
        try {
            List<PetDto> petsDoAbrigo = abrigoService.listarPetsDoAbrigo(idOuNome);
            return ResponseEntity.ok(petsDoAbrigo);
        } catch (ValidacaoException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN)")
    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid CadastroPetDto cadastroPetDto) {
        try {
            Abrigo abrigo = abrigoService.carregarAbrigo(idOuNome);
            petService.cadastrarPet(abrigo, cadastroPetDto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
