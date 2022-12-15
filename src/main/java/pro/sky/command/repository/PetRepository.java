package pro.sky.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.command.model.Pet;

public interface PetRepository extends JpaRepository<Pet,Long> {
}
