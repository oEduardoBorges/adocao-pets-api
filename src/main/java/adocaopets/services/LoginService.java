package adocaopets.services;

import adocaopets.config.security.JwtService;
import adocaopets.dtos.login.CadastroDto;
import adocaopets.dtos.login.Token;
import adocaopets.dtos.login.UsuarioDto;
import adocaopets.models.Usuario;
import adocaopets.models.enums.Role;
import adocaopets.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Token register(CadastroDto cadastroDto) {
        var usuario = Usuario.builder()
                .nome(cadastroDto.nome())
                .email(cadastroDto.email())
                .senha(passwordEncoder.encode(cadastroDto.senha()))
                .role(Role.USER)
                .build();
        usuarioRepository.save(usuario);
        var jwtToken = jwtService.generateToken(usuario);
        return Token.builder()
                .token(jwtToken)
                .build();
    }

    public Token authenticate(UsuarioDto usuarioDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuarioDto.email(),
                        usuarioDto.senha()
                )
        );
        var user = usuarioRepository.findByEmail(usuarioDto.email())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return Token.builder()
                .token(jwtToken)
                .build();
    }
}
