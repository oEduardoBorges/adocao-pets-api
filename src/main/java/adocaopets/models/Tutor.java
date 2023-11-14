package adocaopets.models;

import adocaopets.dtos.tutor.AtualizacaoTutorDto;
import adocaopets.dtos.tutor.CadastroTutorDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tutores")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
    private String telefone;

    @NotBlank
    @Email
    private String email;

    @OneToMany(mappedBy = "tutor")
    private List<Adocao> adocoes;

    public Tutor(CadastroTutorDto cadastroTutorDto) {
        this.nome = cadastroTutorDto.nome();
        this.telefone = cadastroTutorDto.telefone();
        this.email = cadastroTutorDto.email();
    }

    public void atualizarDados(AtualizacaoTutorDto atualizacaoTutorDto) {
        this.nome = atualizacaoTutorDto.nome();
        this.telefone = atualizacaoTutorDto.telefone();
        this.email = atualizacaoTutorDto.email();
    }
}
