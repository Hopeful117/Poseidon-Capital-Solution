package com.nnk.repositories;

import com.nnk.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTests {

    @Autowired
    UserRepository userRepository;


    @Test
    public void userTest() {
        User user = new User("testuser", "Passw0rd!", "Test User", "USER");

        // Save
        user = userRepository.save(user);
        assertNotNull(user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("Passw0rd!", user.getPassword());
        assertEquals("Test User", user.getFullname());
        assertEquals("USER", user.getRole());

        // Update
        user.setUsername("updateduser");
        user.setPassword("NewPass1!");
        user.setFullname("Updated User");
        user.setRole("ADMIN");
        user = userRepository.save(user);
        assertEquals("updateduser", user.getUsername());
        assertEquals("NewPass1!", user.getPassword());
        assertEquals("Updated User", user.getFullname());
        assertEquals("ADMIN", user.getRole());

        // Find
        Optional<User> found = userRepository.findById(user.getId());
        assertTrue(found.isPresent());
        assertEquals("updateduser", found.get().getUsername());

        // Find by username
        Optional<User> foundByUsername = userRepository.findByUsername("updateduser");
        assertTrue(foundByUsername.isPresent());
        assertEquals(user.getId(), foundByUsername.get().getId());

        // Find all
        assertFalse(userRepository.findAll().isEmpty());

        // Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> userOptional = userRepository.findById(id);
        assertFalse(userOptional.isPresent());
    }
}
