package info.servobot.tournaments.data.serializers;

import info.servobot.tournaments.data.entities.UserRow;
import info.servobot.tournaments.data.repositories.UserRepository;
import info.servobot.tournaments.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserSerializer {
    private final UserRepository userRepository;

    public User loadUser(final int id) {
        Optional<UserRow> userRow = userRepository.findById(id);
        if (userRow.isPresent()) {
            return createUser(userRow.get());
        }
        return null;
    }

    public Collection<User> loadUsers(final Iterable<Integer> idsToLoad) {
        Collection<User> users = new ArrayList<>();
        for (UserRow userRow : userRepository.findAllById(idsToLoad)) {
            users.add(createUser(userRow));
        }
        return users;
    }

    public User loadUserByTwitchId(final long id) {
        Optional<UserRow> userRow = userRepository.findByTwitchId(id);
        if (userRow.isPresent()) {
            return createUser(userRow.get());
        }
        return null;
    }

    public void save(final User user) {
        UserRow userRow = new UserRow();
        userRow.setId(user.getId());
        userRow.setFlags(user.getFlags());
        userRow.setTwitchId(user.getTwitchId());
        userRow.setTwitchName(user.getTwitchName());
        userRepository.save(userRow);
        user.setId(userRow.getId());
    }

    private User createUser(final UserRow userRow) {
        return new User(userRow.getId(), userRow.getFlags(), userRow.getTwitchId(), userRow.getTwitchName());
    }
}
