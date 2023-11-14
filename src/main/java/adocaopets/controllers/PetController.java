package adocaopets.controllers;

import adocaopets.models.Pet;
import adocaopets.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetRepository petRepository;

    @GetMapping
    public ResponseEntity<Page<Pet>> listarTodosDisponiveis(Pageable pageable) {
        List<Pet> disponiveis = petRepository.findAll()
                .stream()
                .filter(pet -> !pet.getAdotado())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(disponiveis, pageable, disponiveis.size()));
    }
}
