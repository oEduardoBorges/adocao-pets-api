package adocaopets.controllers;

import adocaopets.dtos.login.CadastroDto;
import adocaopets.dtos.login.Token;
import adocaopets.dtos.login.UsuarioDto;
import adocaopets.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/registro")
    public ResponseEntity<Token> register(@RequestBody CadastroDto cadastroDto) {
      return ResponseEntity.ok(loginService.register(cadastroDto));
    }
    @PostMapping("/logar")
    public ResponseEntity<Token> authenticate(@RequestBody UsuarioDto usuarioDto) {
      return ResponseEntity.ok(loginService.authenticate(usuarioDto));
    }
}
