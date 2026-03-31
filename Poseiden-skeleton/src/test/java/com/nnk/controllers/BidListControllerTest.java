package com.nnk.controllers;

import com.nnk.domain.BidList;
import com.nnk.domain.User;
import com.nnk.repositories.BidListRepository;
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
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class BidListControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BidListRepository bidListRepository;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        User user = new User("user", "Motdep@sse1", "User", "USER");
        userRepository.save(user);

        BidList bidList = new BidList("Account1", "Type1", BigDecimal.valueOf(100.0));
        bidListRepository.save(bidList);

    }


    @Test
    @WithMockUser(username = "user")
    void shouldReturnBidListList() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnAddBidListForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))

                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "user")
    void shouldValidateBidList() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Account2")
                        .param("type", "Type2")
                        .param("bidQuantity", "200.0")
                )
                .andExpect(status().is3xxRedirection());


    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnFormWithErrorIfBindingFails() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "")
                        .param("type", "Type1")
                        .param("bidQuantity", "100.0")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrors("bidList", "account"));


    }


    @Test
    @WithMockUser(username = "user")
    void shouldReturnUpdateForm() throws Exception {
        Optional<BidList> bidList = bidListRepository.findAll().stream().findFirst();
        int id = bidList.get().getId();

        mockMvc.perform(get("/bidList/update/" + id))


                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("bidList/update"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldRedirectToListIfExceptionThrown() throws Exception {
        mockMvc.perform(get("/bidList/update/" + 999))


                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"));


    }

    @Test
    @WithMockUser(username = "user")
    void shouldUpdateBidList() throws Exception {
        Optional<BidList> bidList = bidListRepository.findAll().stream().findFirst();
        int id = bidList.get().getId();
        mockMvc.perform(post("/bidList/update/" + id)
                        .param("account", "UpdatedAccount")
                        .param("type", "UpdatedType")
                        .param("bidQuantity", "150.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldRedirectWithErrorIfBindingFails() throws Exception {
        Optional<BidList> bidList = bidListRepository.findAll().stream().findFirst();
        int id = bidList.get().getId();

        mockMvc.perform(post("/bidList/update/" + id)
                        .param("account", "")
                        .param("type", "Type1")
                        .param("bidQuantity", "100.0"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrors("bidList", "account"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnFormWithErrorIfExceptionThrown() throws Exception {
        mockMvc.perform(post("/bidList/update/" + 999)
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("bidQuantity", "100.0"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(view().name("bidList/update"));


    }

    @Test
    @WithMockUser(username = "user")
    void shouldDeleteBidList() throws Exception {
        Optional<BidList> bidList = bidListRepository.findAll().stream().findFirst();
        int id = bidList.get().getId();
        mockMvc.perform(get("/bidList/delete/" + id))

                .andExpect(model().attributeDoesNotExist("errorMessage"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldDisplayErrorIfExceptionWhenDeleting() throws Exception {
        mockMvc.perform(get("/bidList/delete/" + 999))

                .andExpect(flash().attributeExists("errorMessage"));


    }


}
