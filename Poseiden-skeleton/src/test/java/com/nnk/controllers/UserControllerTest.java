package com.nnk.controllers;

import com.nnk.domain.User;
import com.nnk.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        User adminUser = new User("admin", "Motdep@sse1", "Admin User", "ADMIN");
        userRepository.save(adminUser);

        User testUser = new User("testuser", "TestPass1!", "Test User", "USER");
        userRepository.save(testUser);
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldReturnUserList() throws Exception {
        mockMvc.perform(get("/user/list")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("admin")))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldReturnAddUserForm() throws Exception {
        mockMvc.perform(get("/user/add")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("admin")))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @ParameterizedTest
    @CsvSource({
        "'newuser1', 'ValidPass1!', 'New User One', 'USER'",
        "'newuser2', 'Another2@', 'New User Two', 'ADMIN'",
        "'newuser3', 'ThirdPass3#', 'New User Three', 'MODERATOR'"
    })
    @WithMockUser(username = "admin")
    void shouldValidateUser(String username, String password, String fullname, String role) throws Exception {
        mockMvc.perform(post("/user/validate")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("admin"))
                        .param("username", username)
                        .param("password", password)
                        .param("fullname", fullname)
                        .param("role", role)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @ParameterizedTest
    @CsvSource({
        "'', 'ValidPass1!', 'User', 'USER'",  // empty username
        "'user', '', 'User', 'USER'",  // empty password
        "'user', 'short', 'User', 'USER'",  // invalid password (too short, no upper, no digit, no symbol)
        "'user', 'nouppercase1!', 'User', 'USER'",  // no uppercase
        "'user', 'NODIGIT!', 'User', 'USER'",  // no digit
        "'user', 'NoSymbol1', 'User', 'USER'",  // no symbol
        "'user', 'ValidPass1!', '', 'USER'",  // empty fullname
        "'user', 'ValidPass1!', 'User', ''"  // empty role
    })
    @WithMockUser(username = "admin")
    void shouldReturnFormWithErrorIfBindingFails(String username, String password, String fullname, String role) throws Exception {
        mockMvc.perform(post("/user/validate")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("admin"))
                        .param("username", username)
                        .param("password", password)
                        .param("fullname", fullname)
                        .param("role", role)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/add"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldReturnUpdateForm() throws Exception {
        Optional<User> user = userRepository.findByUsername("testuser");
        int id = user.get().getId();

        mockMvc.perform(get("/user/update/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("admin")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldRedirectToListIfExceptionThrown() throws Exception {
        mockMvc.perform(get("/user/update/" + 999)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("admin")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @ParameterizedTest
    @CsvSource({
        "'updateduser1', 'Updated1!', 'Updated User One', 'USER'",
        "'updateduser2', 'Changed2@', 'Updated User Two', 'ADMIN'"
    })
    @WithMockUser(username = "admin")
    void shouldUpdateUser(String newUsername, String newPassword, String newFullname, String newRole) throws Exception {
        Optional<User> user = userRepository.findByUsername("testuser");
        int id = user.get().getId();

        mockMvc.perform(post("/user/update/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("admin"))
                        .param("username", newUsername)
                        .param("password", newPassword)
                        .param("fullname", newFullname)
                        .param("role", newRole))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @ParameterizedTest
    @CsvSource({
        "'', 'ValidPass1!', 'User', 'USER'",  // empty username
        "'user', '', 'User', 'USER'",  // empty password
        "'user', 'short', 'User', 'USER'",  // invalid password
        "'user', 'ValidPass1!', '', 'USER'",  // empty fullname
        "'user', 'ValidPass1!', 'User', ''"  // empty role
    })
    @WithMockUser(username = "admin")
    void shouldRedirectWithErrorIfBindingFailsOnUpdate(String username, String password, String fullname, String role) throws Exception {
        Optional<User> user = userRepository.findByUsername("testuser");
        int id = user.get().getId();

        mockMvc.perform(post("/user/update/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("admin"))
                        .param("username", username)
                        .param("password", password)
                        .param("fullname", fullname)
                        .param("role", role))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldReturnFormWithErrorIfExceptionThrownOnUpdate() throws Exception {
        mockMvc.perform(post("/user/update/" + 999)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("admin"))
                        .param("username", "user")
                        .param("password", "ValidPass1!")
                        .param("fullname", "User")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldDeleteUser() throws Exception {
        Optional<User> user = userRepository.findByUsername("testuser");
        int id = user.get().getId();

        mockMvc.perform(get("/user/delete/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("admin")))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/list"))
                .andExpect(model().attributeDoesNotExist("errorMessage"));
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldDisplayErrorIfExceptionWhenDeleting() throws Exception {
        mockMvc.perform(get("/user/delete/" + 999)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("admin")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(view().name("/user/list"));
    }
}
