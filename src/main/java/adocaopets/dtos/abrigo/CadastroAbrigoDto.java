package adocaopets.dtos.abrigo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

public record CadastroAbrigoDto(

        @NotBlank
        String nome,

        @NotBlank
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
        String telefone,

        @NotBlank
        @Email
        String email
){
        public CadastroAbrigoDto {
                if(nome != null) nome = nome.trim();
        }
}
