package pro.sky.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.command.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner,String> {

}
