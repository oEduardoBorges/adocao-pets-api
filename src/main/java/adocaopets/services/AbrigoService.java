package adocaopets.services;

import adocaopets.dtos.abrigo.CadastroAbrigoDto;
import adocaopets.dtos.abrigo.AbrigoDto;
import adocaopets.dtos.pet.PetDto;
import adocaopets.exceptions.ValidacaoException;
import adocaopets.models.Abrigo;
import adocaopets.repositories.AbrigoRepository;
import adocaopets.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AbrigoService {

    private final AbrigoRepository abrigoRepository;

    private final PetRepository petRepository;

    public void cadatrar(CadastroAbrigoDto cadastroAbrigoDto) {
        boolean jaCadastrado = abrigoRepository.existsByNomeOrTelefoneOrEmail(
                cadastroAbrigoDto.nome(), cadastroAbrigoDto.telefone(), cadastroAbrigoDto.email());

        if (jaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        }

        abrigoRepository.save(new Abrigo(cadastroAbrigoDto));
    }

    public Page<AbrigoDto> listar(Pageable pageable) {
        return abrigoRepository
                .findAll(pageable)
                .map(AbrigoDto::new);
    }

    public List<PetDto> listarPetsDoAbrigo(String idOuNome) {
        Abrigo abrigo = carregarAbrigo(idOuNome);

        return petRepository
                .findByAbrigo(abrigo)
                .stream()
                .map(PetDto::new)
                .toList();
    }

    public Abrigo carregarAbrigo(String idOuNome) {
        Optional<Abrigo> optional;
        try {
            Long id = Long.parseLong(idOuNome);
            optional = abrigoRepository.findById(id);
        } catch (NumberFormatException exception) {
            optional = abrigoRepository.findByNome(idOuNome);
        }

        return optional.orElseThrow(() -> new ValidacaoException("Abrigo não encontrado"));
    }
}
