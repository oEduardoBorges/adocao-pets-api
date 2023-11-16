package adocaopets.dtos.login;

import lombok.Builder;

@Builder
public record Token(

        String token
){}
