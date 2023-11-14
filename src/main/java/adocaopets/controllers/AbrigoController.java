package adocaopets.controllers;

import adocaopets.dtos.abrigo.AbrigoDto;
import adocaopets.dtos.abrigo.CadastroAbrigoDto;
import adocaopets.dtos.pet.CadastroPetDto;
import adocaopets.dtos.pet.PetDto;
import adocaopets.exceptions.ValidacaoException;
import adocaopets.models.Abrigo;
import adocaopets.models.Pet;
import adocaopets.repositories.AbrigoRepository;
import adocaopets.services.AbrigoService;
import adocaopets.services.PetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
@RequiredArgsConstructor
public class AbrigoController {

    private final AbrigoService abrigoService;

    private final PetService petService;

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
    public ResponseEntity<Page<AbrigoDto>> listar(Pageable pageable) {
        Page<AbrigoDto> abrigos = abrigoService.listar(pageable);
        return ResponseEntity.ok(abrigos);
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
