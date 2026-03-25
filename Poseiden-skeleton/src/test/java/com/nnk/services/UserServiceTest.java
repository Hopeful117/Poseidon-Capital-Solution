package com.nnk.services;

import com.nnk.domain.User;
import com.nnk.exceptions.EntityNotFoundException;
import com.nnk.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @ParameterizedTest
    @CsvSource({"'user1', 'Passw0rd!', 'John Doe', 'ADMIN'",
            "'user2', 'Secret1!', 'Jane Smith', 'USER'",
            "'user3', 'MyPass123#', 'Bob Johnson', 'MODERATOR'"
    })
    public void testAddUser(String username, String password, String fullname, String role) {

        User user = new User(username, password, fullname, role);
        // When
        userService.create(user);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        // Then
        verify(userRepository).save(captor.capture());
        User savedUser = captor.getValue();
        assertEquals(savedUser.getUsername(), username);
        assertNotEquals(savedUser.getPassword(), password); // Password is encoded
        assertEquals(savedUser.getFullname(), fullname);
        assertEquals(savedUser.getRole(), role);


    }


    @Test
    public void testGetAllUsers() {
        //Given

        List<User> mockUsers = List.of(
                new User("user1", "Passw0rd!", "John Doe", "ADMIN"),
                new User("user2", "Secret1!", "Jane Smith", "USER")
        );
        when(userRepository.findAll()).thenReturn(mockUsers);
        // When
        List<User> users = userService.findAll();


        // Then
        assert (users.size() == 2);
        assert (users.get(0).getUsername().equals("user1"));
        assert (users.get(1).getPassword().equals("Secret1!"));
        assert (users.get(0).getFullname().equals("John Doe"));
        assert (users.get(1).getRole().equals("USER"));


    }

    @Test
    public void testGetUserById() {
        // Given
        User mockUser = new User("user1", "Passw0rd!", "John Doe", "ADMIN");
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(mockUser));

        // When
        User user = userService.findById(1);

        // Then
        assert (user.getUsername().equals("user1"));
        assert (user.getPassword().equals("Passw0rd!"));
        assert (user.getFullname().equals("John Doe"));
        assert (user.getRole().equals("ADMIN"));
    }



    @Test
    public void testGetUserByIdShouldThrowExceptionWhenUserNotFound() {
        // Given
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> userService.findById(999));

    }

    @ParameterizedTest
    @CsvSource({
        "1, 'user1', 'Passw0rd!', 'John Doe', 'ADMIN', 'updatedUser', 'NewPass1!', 'Jane Doe', 'USER'",
        "2, 'user2', 'Secret1!', 'Jane Smith', 'USER', 'updatedUser2', 'Another2@', 'Bob Smith', 'MODERATOR'",
        "3, 'user3', 'MyPass123#', 'Bob Johnson', 'MODERATOR', 'updatedUser3', 'Final3$', 'Alice Johnson', 'ADMIN'"
    })
    public void testUpdateUser(int id, String originalUsername, String originalPassword, String originalFullname, String originalRole,
                                String newUsername, String newPassword, String newFullname, String newRole) {
        // Given
        User existingUser = new User(originalUsername, originalPassword, originalFullname, originalRole);
        existingUser.setId(id);
        
        User updatedUser = new User(newUsername, newPassword, newFullname, newRole);
        updatedUser.setId(id);
        
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(existingUser));

        // When
        userService.update(updatedUser);

        // Then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User savedUser = captor.getValue();
        
        assertEquals(newUsername, savedUser.getUsername());
        assertNotEquals(newPassword, savedUser.getPassword()); // Password is encoded
        assertEquals(newFullname, savedUser.getFullname());
        assertEquals(newRole, savedUser.getRole());
        assertEquals(id, savedUser.getId());
    }

    @Test
    public void testUpdateUserShouldThrowExceptionWhenUserNotFound() {
        // Given
        User userToUpdate = new User("user1", "Passw0rd!", "John Doe", "ADMIN");
        userToUpdate.setId(999);
        
        when(userRepository.findById(999)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> userService.update(userToUpdate));
    }

}
