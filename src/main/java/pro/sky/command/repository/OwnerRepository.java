package pro.sky.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.command.model.Owner;
/**
 * Репозиторий для сущности Owner
 * хранятся данные о пользователе: идентификатор чата, Имя(как в чате телеграм), идентификаторы животных взятых из приюта.
 * @autor Наталья Шилова
 */
public interface OwnerRepository extends JpaRepository<Owner,String> {

}
