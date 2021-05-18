package info.servobot.tournaments.conductors;

import info.servobot.tournaments.data.serializers.UserSerializer;
import info.servobot.tournaments.model.User;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class UserConductor {
    private final UserSerializer userSerializer;
    private final Map<Integer, User> userIdCache = new ConcurrentHashMap<>();
    private final Map<Long, User> twitchIdCache = new ConcurrentHashMap<>();

    public User getUser(final int id) {
        if (userIdCache.containsKey(id)) {
            return userIdCache.get(id);
        }
        return cache(userSerializer.loadUser(id));
    }

    public User getUserByTwitchId(final long twitchId, final String twitchUsername) {
        if (twitchIdCache.containsKey(twitchId)) {
            return twitchIdCache.get(twitchId);
        }
        User user = userSerializer.loadUserByTwitchId(twitchId);
        if (user != null) {
            return cache(user);
        }

        user = new User(User.UNREGISTERED_ID, User.DEFAULT_FLAGS, twitchId, twitchUsername);
        userSerializer.save(user);
        return cache(user);
    }

    private User cache(final User user) {
        userIdCache.put(user.getId(), user);
        twitchIdCache.put(user.getTwitchId(), user);
        return user;
    }

    public void loadUsers(final Iterable<Integer> ids) {
        Set<Integer> usersToLoad = new HashSet<>();
        for (Integer id : ids) {
            if (!userIdCache.containsKey(id)) {
                usersToLoad.add(id);
            }
        }
        userSerializer.loadUsers(usersToLoad).forEach(user -> cache(user));
    }
}