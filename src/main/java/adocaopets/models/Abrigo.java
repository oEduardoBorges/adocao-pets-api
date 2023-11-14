package adocaopets.models;

import adocaopets.dtos.abrigo.CadastroAbrigoDto;
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
@Table(name = "abrigos")
public class Abrigo {

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

    @OneToMany(mappedBy = "abrigo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pet> pets;

    public Abrigo(CadastroAbrigoDto cadastroAbrigoDto) {
        this.nome = cadastroAbrigoDto.nome();
        this.telefone = cadastroAbrigoDto.telefone();
        this.email = cadastroAbrigoDto.email();
    }
}
