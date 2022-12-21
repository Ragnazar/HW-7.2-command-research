package pro.sky.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.command.model.Pet;
@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
}
