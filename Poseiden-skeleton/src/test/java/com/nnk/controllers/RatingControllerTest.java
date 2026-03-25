package com.nnk.controllers;

import com.nnk.domain.Rating;
import com.nnk.domain.User;
import com.nnk.repositories.RatingRepository;
import com.nnk.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class RatingControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RatingRepository ratingRepository;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        User user = new User("user", "Motdep@sse1", "User", "USER");
        userRepository.save(user);

        Rating rating = new Rating("1", "1", "1", 1);
        ratingRepository.save(rating);

    }


    @Test
    @WithMockUser(username = "user")
    void shouldReturnRatingList() throws Exception {
        mockMvc.perform(get("/rating/list")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnAddRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldValidateRating() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user"))
                        .param("moodysRating", "1")
                        .param("sandPRating", "1")
                        .param("fitchRating", "1")
                        .param("orderNumber", "5")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));


    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnFormWithErrorIfBindingFails() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user"))
                        .param("moodysRating", "5")
                        .param("sandPRating", "1")
                        .param("fitchRating", "1")
                        .param("orderNumber", "-1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"));


    }


    @Test
    @WithMockUser(username = "user")
    void shouldReturnUpdateForm() throws Exception {
        Optional<Rating> rating = ratingRepository.findAll().stream().findFirst();
        int id = rating.get().getId();

        mockMvc.perform(get("/rating/update/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))

                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("rating"))
                .andExpect(view().name("rating/update"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldRedirectToListIfExceptionThrown() throws Exception {
        mockMvc.perform(get("/rating/update/" + 5)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))

                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"));


    }

    @Test
    @WithMockUser(username = "user")
    void shouldUpdateRating() throws Exception {
        Optional<Rating> rating = ratingRepository.findAll().stream().findFirst();
        int id = rating.get().getId();
        mockMvc.perform(post("/rating/update/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user"))
                        .param("moodysRating", "5")
                        .param("sandPRating", "1")
                        .param("fitchRating", "1")
                        .param("orderNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldRedirectWithErrorIfBindingFails() throws Exception {
        Optional<Rating> rating = ratingRepository.findByOrderNumber(1);
        int id = rating.get().getId();
        mockMvc.perform(post("/rating/update/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user"))
                        .param("moodysRating", "5")
                        .param("sandPRating", "1")
                        .param("fitchRating", "1")
                        .param("orderNumber", "-1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnFormWithErrorIfExceptionThrown() throws Exception {
        mockMvc.perform(post("/rating/update/" + 111)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user"))
                        .param("moodysRating", "5")
                        .param("sandPRating", "1")
                        .param("fitchRating", "1")
                        .param("orderNumber", "1"))

                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(view().name("rating/update"));


    }

    @Test
    @WithMockUser(username = "user")
    void shouldDeleteRating() throws Exception {
        Optional<Rating> rating = ratingRepository.findByOrderNumber(1);
        int id = rating.get().getId();
        mockMvc.perform(get("/rating/delete/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(model().attributeDoesNotExist("errorMessage"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldDisplayErrorIfExceptionWhenDeleting() throws Exception {
        mockMvc.perform(get("/rating/delete/" + 111)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(model().attributeExists("errorMessage"));

    }


}
