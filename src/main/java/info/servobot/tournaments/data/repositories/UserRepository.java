package info.servobot.tournaments.data.repositories;

import info.servobot.tournaments.data.entities.UserRow;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserRow, Integer> {
    Optional<UserRow> findByTwitchId(long twitchId);
}
