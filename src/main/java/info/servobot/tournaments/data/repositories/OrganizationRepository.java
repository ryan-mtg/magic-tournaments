package info.servobot.tournaments.data.repositories;

import info.servobot.tournaments.data.entities.OrganizationRow;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<OrganizationRow, Integer> {
}
