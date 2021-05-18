package info.servobot.tournaments.data.repositories;

import info.servobot.tournaments.data.entities.OrganizationUserRow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationUserRepository
        extends CrudRepository<OrganizationUserRow, OrganizationUserRow.OrganizationUserId> {
}
