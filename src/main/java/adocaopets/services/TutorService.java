package adocaopets.services;

import adocaopets.dtos.tutor.AtualizacaoTutorDto;
import adocaopets.dtos.tutor.CadastroTutorDto;
import adocaopets.dtos.tutor.ListarTutoresDto;
import adocaopets.exceptions.ValidacaoException;
import adocaopets.models.Tutor;
import adocaopets.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;

    public void cadastrar(CadastroTutorDto cadastroTutorDto) {
        boolean jaCadastrado = tutorRepository.existsByTelefoneOrEmail(cadastroTutorDto.telefone(), cadastroTutorDto.email());

        if (jaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        }

        tutorRepository.save(new Tutor(cadastroTutorDto));
    }

    public Page<ListarTutoresDto> listar(Pageable pageable) {
        Page<Tutor> tutorPage = tutorRepository.findAll(pageable);

        List<ListarTutoresDto> dto = tutorPage.getContent().stream()
                .map(tutor -> new ListarTutoresDto(tutor.getId(), tutor.getNome(), tutor.getTelefone(), tutor.getEmail()))
                .collect(Collectors.toList());

        return new PageImpl<>(dto, pageable, tutorPage.getTotalElements());
    }

    public void atualizar(AtualizacaoTutorDto atualizacaoTutorDto) {
        Tutor tutor = tutorRepository.getReferenceById(atualizacaoTutorDto.id());
        tutor.atualizarDados(atualizacaoTutorDto);
    }
}
