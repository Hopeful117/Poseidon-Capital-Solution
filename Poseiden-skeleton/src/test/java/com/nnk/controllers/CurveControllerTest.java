package com.nnk.controllers;

import com.nnk.domain.CurvePoint;
import com.nnk.domain.User;
import com.nnk.repositories.CurvePointRepository;
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

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class CurveControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CurvePointRepository curvePointRepository;

    @Autowired
    private MockMvc mockMvc;

    private CurvePoint savedCurvePoint;

    @BeforeEach
    void setUp() {
        User user = new User("user", "Motdep@sse1", "User", "USER");
        userRepository.save(user);

        CurvePoint curvePoint = new CurvePoint(BigDecimal.valueOf(1.0), BigDecimal.valueOf(1.0));
        savedCurvePoint = curvePointRepository.save(curvePoint);
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnCurvePointList() throws Exception {
        mockMvc.perform(get("/curvePoint/list")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnAddCurveForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldValidateCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user"))
                        .param("curveId", "1")
                        .param("term", "1.0")
                        .param("value", "1.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnFormWithErrorIfBindingFails() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user"))
                        .param("curveId", "1")
                        .param("term", "a")
                        .param("value", "1.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnUpdateForm() throws Exception {
        int id = savedCurvePoint.getId();

        mockMvc.perform(get("/curvePoint/update/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldRedirectToListIfExceptionThrown() throws Exception {
        mockMvc.perform(get("/curvePoint/update/" + 999)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldUpdateCurvePoint() throws Exception {
        int id = savedCurvePoint.getId();
        mockMvc.perform(post("/curvePoint/update/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user"))
                        .param("curveId", "5")
                        .param("term", "2.0")
                        .param("value", "3.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldRedirectWithErrorIfBindingFails() throws Exception {
        int id = savedCurvePoint.getId();
        mockMvc.perform(post("/curvePoint/update/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user"))
                        .param("curveId", "1")
                        .param("term", "a")
                        .param("value", "1.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnFormWithErrorIfExceptionThrown() throws Exception {
        mockMvc.perform(post("/curvePoint/update/" + 999)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user"))
                        .param("curveId", "5")
                        .param("term", "2.0")
                        .param("value", "3.0"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldDeleteCurvePoint() throws Exception {
        int id = savedCurvePoint.getId();
        mockMvc.perform(get("/curvePoint/delete/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldDisplayErrorIfExceptionWhenDeleting() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/" + 999)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(view().name("curvePoint/list"));
    }
}
