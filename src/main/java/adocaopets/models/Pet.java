package adocaopets.models;

import adocaopets.dtos.pet.CadastroPetDto;
import adocaopets.models.enums.TipoPet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoPet tipo;

    @NotBlank
    private String nome;

    @NotBlank
    private String raca;

    @NotNull
    private Integer idade;

    @NotBlank
    private String cor;

    @NotNull
    private Float peso;

    private Boolean adotado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Abrigo abrigo;

    @OneToOne(mappedBy = "pet", fetch = FetchType.LAZY)
    private Adocao adocao;

    public Pet(CadastroPetDto cadastroPetDto, Abrigo abrigo) {
        this.tipo = cadastroPetDto.tipo();
        this.nome = cadastroPetDto.nome();
        this.raca = cadastroPetDto.raca();
        this.idade = cadastroPetDto.idade();
        this.cor = cadastroPetDto.cor();
        this.peso = cadastroPetDto.peso();
        this.abrigo = abrigo;
        this.adotado = false;
    }
}
