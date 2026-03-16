package com.nnk.controllers;

import com.nnk.domain.Rating;
import com.nnk.domain.User;
import com.nnk.repositories.RatingRepository;
import com.nnk.repositories.UserRepository;
import com.nnk.services.RatingService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
    void setUp(){
        User user = new User("user","password","User","USER");
        userRepository.save(user);

        Rating rating = new Rating("1","1","1",1);
        ratingRepository.save(rating);

    }


    @Test
    @WithMockUser(username="user")
    void shouldReturnRatingList() throws Exception {
        mockMvc.perform(get("/rating/list")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));

    }

    @Test
    @WithMockUser(username="user")
    void shouldReturnAddRatingForm() throws Exception{
        mockMvc.perform(get("/rating/add")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

    }
}
