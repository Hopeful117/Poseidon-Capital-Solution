package com.nnk.controllers;

import com.nnk.domain.Trade;
import com.nnk.domain.User;
import com.nnk.repositories.TradeRepository;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class TradeControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        User user = new User("user", "Motdep@sse1", "User", "USER");
        userRepository.save(user);

        Trade trade = new Trade("Account1", "Type1", BigDecimal.valueOf(100.50));
        tradeRepository.save(trade);
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnTradeList() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnAddTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldValidateTrade() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "Account1")
                        .param("type", "Type1")
                        .param("buyQuantity", "100.50"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnFormWithErrorIfBindingFails() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .param("type", "Type1")
                        .param("buyQuantity", "-100.50"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrors("trade", "account", "buyQuantity"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnUpdateForm() throws Exception {
        Optional<Trade> trade = tradeRepository.findAll().stream().findFirst();
        int id = trade.get().getTradeId();

        mockMvc.perform(get("/trade/update/" + id))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithMockUser(username = "user")
    void shouldRedirectToListIfExceptionThrown() throws Exception {
        mockMvc.perform(get("/trade/update/" + 999))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldUpdateTrade() throws Exception {
        Optional<Trade> trade = tradeRepository.findAll().stream().findFirst();
        int id = trade.get().getTradeId();
        mockMvc.perform(post("/trade/update/" + id)
                        .param("account", "UpdatedAccount")
                        .param("type", "UpdatedType")
                        .param("buyQuantity", "200.75"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldRedirectWithErrorIfBindingFails() throws Exception {
        Optional<Trade> trade = tradeRepository.findAll().stream().findFirst();
        int id = trade.get().getTradeId();
        mockMvc.perform(post("/trade/update/" + id)
                        .param("account", "")
                        .param("type", "Type1")
                        .param("buyQuantity", "-100.50"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrors("trade", "buyQuantity", "account"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnFormWithErrorIfExceptionThrown() throws Exception {
        mockMvc.perform(post("/trade/update/" + 999)
                        .param("account", "Account1")
                        .param("type", "Type1")
                        .param("buyQuantity", "100.50"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldDeleteTrade() throws Exception {
        Optional<Trade> trade = tradeRepository.findAll().stream().findFirst();
        int id = trade.get().getTradeId();
        mockMvc.perform(get("/trade/delete/" + id))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    @WithMockUser(username = "user")
    void shouldDisplayErrorIfExceptionWhenDeleting() throws Exception {
        mockMvc.perform(get("/trade/delete/" + 999))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"));
    }
}

