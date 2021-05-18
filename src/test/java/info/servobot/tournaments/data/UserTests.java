package info.servobot.tournaments.data;

import info.servobot.tournaments.data.entities.UserRow;
import info.servobot.tournaments.data.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveOneUser() {
        UserRow userRow = new UserRow(1, "name");
        userRepository.save(userRow);

        assertTrue(userRepository.findById(userRow.getId()).isPresent());
        assertEquals("name", userRepository.findById(userRow.getId()).get().getTwitchName());
    }

    @Test
    void testSaveMultipleUsers() {
        UserRow userRow = new UserRow(1, "name");
        UserRow otherUserRow = new UserRow(1, "othername");
        userRepository.saveAll(Arrays.asList(userRow, otherUserRow));

        assertTrue(userRepository.findById(userRow.getId()).isPresent());
        assertEquals("name", userRepository.findById(userRow.getId()).get().getTwitchName());

        assertTrue(userRepository.findById(otherUserRow.getId()).isPresent());
        assertEquals("othername", userRepository.findById(otherUserRow.getId()).get().getTwitchName());
    }
}