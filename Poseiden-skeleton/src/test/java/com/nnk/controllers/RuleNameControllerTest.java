package com.nnk.controllers;

import com.nnk.domain.RuleName;
import com.nnk.domain.User;
import com.nnk.repositories.RuleNameRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class RuleNameControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RuleNameRepository ruleNameRepository;

    @Autowired
    private MockMvc mockMvc;

    private RuleName savedRuleName;

    @BeforeEach
    void setUp() {
        User user = new User("user", "Motdep@sse1", "User", "USER");
        userRepository.save(user);

        RuleName ruleName = new RuleName("Rule1", "Description1", "json1", "template1", "sql1", "part1");
        savedRuleName = ruleNameRepository.save(ruleName);
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnRuleNameList() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnAddRuleForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldValidateRuleName() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "Rule1")
                        .param("description", "Description1")
                        .param("json", "json1")
                        .param("template", "template1")
                        .param("sqlStr", "sql1")
                        .param("sqlPart", "part1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnFormWithErrorIfBindingFails() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "")
                        .param("description", "Description1")
                        .param("json", "json1")
                        .param("template", "template1")
                        .param("sqlStr", "sql1")
                        .param("sqlPart", "part1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnUpdateForm() throws Exception {
        int id = savedRuleName.getId();

        mockMvc.perform(get("/ruleName/update/" + id))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldRedirectToListIfExceptionThrown() throws Exception {
        mockMvc.perform(get("/ruleName/update/" + 999))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldUpdateRuleName() throws Exception {
        int id = savedRuleName.getId();
        mockMvc.perform(post("/ruleName/update/" + id)
                        .param("name", "UpdatedRule")
                        .param("description", "UpdatedDescription")
                        .param("json", "updatedJson")
                        .param("template", "updatedTemplate")
                        .param("sqlStr", "updatedSql")
                        .param("sqlPart", "updatedPart"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldRedirectWithErrorIfBindingFails() throws Exception {
        int id = savedRuleName.getId();
        mockMvc.perform(post("/ruleName/update/" + id)
                        .param("name", "")
                        .param("description", "Description1")
                        .param("json", "json1")
                        .param("template", "template1")
                        .param("sqlStr", "sql1")
                        .param("sqlPart", "part1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnFormWithErrorIfExceptionThrown() throws Exception {
        mockMvc.perform(post("/ruleName/update/" + 999)
                        .param("name", "Rule1")
                        .param("description", "Description1")
                        .param("json", "json1")
                        .param("template", "template1")
                        .param("sqlStr", "sql1")
                        .param("sqlPart", "part1"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldDeleteRuleName() throws Exception {
        int id = savedRuleName.getId();
        mockMvc.perform(get("/ruleName/delete/" + id))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    @WithMockUser(username = "user")
    void shouldDisplayErrorIfExceptionWhenDeleting() throws Exception {
        mockMvc.perform(get("/ruleName/delete/" + 999))
                .andExpect(flash().attributeExists("errorMessage"));

    }
}
