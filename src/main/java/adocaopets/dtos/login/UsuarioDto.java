package adocaopets.dtos.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UsuarioDto(

        @NotBlank
        String email,

        @NotBlank
        String senha
){}
