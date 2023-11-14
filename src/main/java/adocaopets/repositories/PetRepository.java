package adocaopets.repositories;

import adocaopets.models.Abrigo;
import adocaopets.models.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Page<Pet> findAllByAdotadoFalse(Pageable pageable);

    List<Pet> findByAbrigo(Abrigo abrigo);
}
