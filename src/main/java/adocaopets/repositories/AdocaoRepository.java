package adocaopets.repositories;

import adocaopets.models.Adocao;
import adocaopets.models.enums.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    boolean existsByPetIdAndStatus(Long idPet, StatusAdocao status);

    boolean existsByTutorIdAndStatus(Long idPet, StatusAdocao status);

    Integer countByTutorIdAndStatus(Long idTutor, StatusAdocao status);
}
