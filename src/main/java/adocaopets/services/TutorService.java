package adocaopets.services;

import adocaopets.dtos.tutor.AtualizacaoTutorDto;
import adocaopets.dtos.tutor.CadastroTutorDto;
import adocaopets.exceptions.ValidacaoException;
import adocaopets.models.Tutor;
import adocaopets.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void atualizar(AtualizacaoTutorDto atualizacaoTutorDto) {
        Tutor tutor = tutorRepository.getReferenceById(atualizacaoTutorDto.id());
        tutor.atualizarDados(atualizacaoTutorDto);
    }
}
