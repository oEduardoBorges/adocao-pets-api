package adocaopets.repositories;

import adocaopets.models.Abrigo;
import adocaopets.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbrigoRepository extends JpaRepository<Abrigo, Long> {

    Optional<Abrigo> findByNome(String nome);

    boolean existsByNomeOrTelefoneOrEmail(String nome, String telefone, String email);

    Optional<Usuario> findByEmail(String email);
}
