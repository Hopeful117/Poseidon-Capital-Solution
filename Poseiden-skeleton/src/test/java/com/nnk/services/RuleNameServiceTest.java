package com.nnk.services;

import com.nnk.domain.RuleName;
import com.nnk.exceptions.EntityNotFoundException;
import com.nnk.repositories.RuleNameRepository;
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

public class RuleNameServiceTest {


    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    @ParameterizedTest
    @CsvSource({"'Rule1', 'Description1', 'json1', 'template1', 'sql1', 'part1'",
            "'Rule2', 'Description2', 'json2', 'template2', 'sql2', 'part2'",
            "'Rule3', 'Description3', 'json3', 'template3', 'sql3', 'part3'"
    })
    public void testAddRuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {

        RuleName ruleName = new RuleName(name, description, json, template, sqlStr, sqlPart);
        // When
        ruleNameService.create(ruleName);
        ArgumentCaptor<RuleName> captor = ArgumentCaptor.forClass(RuleName.class);

        // Then
        verify(ruleNameRepository).save(captor.capture());
        RuleName savedRuleName = captor.getValue();
        assertEquals(savedRuleName.getName(), name);
        assertEquals(savedRuleName.getDescription(), description);
        assertEquals(savedRuleName.getJson(), json);
        assertEquals(savedRuleName.getTemplate(), template);
        assertEquals(savedRuleName.getSqlStr(), sqlStr);
        assertEquals(savedRuleName.getSqlPart(), sqlPart);


    }


    @Test
    public void testGetAllRuleNames() {
        //Given

        List<RuleName> mockRuleNames = List.of(
                new RuleName("Rule1", "Desc1", "json1", "temp1", "sql1", "part1"),
                new RuleName("Rule2", "Desc2", "json2", "temp2", "sql2", "part2")
        );
        when(ruleNameRepository.findAll()).thenReturn(mockRuleNames);
        // When
        List<RuleName> ruleNames = ruleNameService.findAll();


        // Then
        assert (ruleNames.size() == 2);
        assert (ruleNames.get(0).getName().equals("Rule1"));
        assert (ruleNames.get(1).getDescription().equals("Desc2"));
        assert (ruleNames.get(0).getJson().equals("json1"));
        assert (ruleNames.get(1).getTemplate().equals("temp2"));


    }

    @Test
    public void testGetRuleNameById() {
        // Given
        RuleName mockRuleName = new RuleName("Rule1", "Desc1", "json1", "temp1", "sql1", "part1");
        when(ruleNameRepository.findById(anyInt())).thenReturn(java.util.Optional.of(mockRuleName));

        // When
        RuleName ruleName = ruleNameService.findById(1);

        // Then
        assert (ruleName.getName().equals("Rule1"));
        assert (ruleName.getDescription().equals("Desc1"));
        assert (ruleName.getJson().equals("json1"));
        assert (ruleName.getTemplate().equals("temp1"));
        assert (ruleName.getSqlStr().equals("sql1"));
        assert (ruleName.getSqlPart().equals("part1"));
    }



    @Test
    public void testGetRuleNameByIdShouldThrowExceptionWhenRuleNameNotFound() {
        // Given
        when(ruleNameRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> ruleNameService.findById(999));

    }

    @ParameterizedTest
    @CsvSource({
        "1, 'Rule1', 'Desc1', 'json1', 'temp1', 'sql1', 'part1', 'UpdatedRule', 'UpdatedDesc', 'updatedJson', 'updatedTemp', 'updatedSql', 'updatedPart'",
        "2, 'Rule2', 'Desc2', 'json2', 'temp2', 'sql2', 'part2', 'UpdatedRule2', 'UpdatedDesc2', 'updatedJson2', 'updatedTemp2', 'updatedSql2', 'updatedPart2'",
        "3, 'Rule3', 'Desc3', 'json3', 'temp3', 'sql3', 'part3', 'UpdatedRule3', 'UpdatedDesc3', 'updatedJson3', 'updatedTemp3', 'updatedSql3', 'updatedPart3'"
    })
    public void testUpdateRuleName(int id, String originalName, String originalDesc, String originalJson, String originalTemp, String originalSql, String originalPart,
                                String newName, String newDesc, String newJson, String newTemp, String newSql, String newPart) {
        // Given
        RuleName existingRuleName = new RuleName(originalName, originalDesc, originalJson, originalTemp, originalSql, originalPart);
        existingRuleName.setId(id);
        
        RuleName updatedRuleName = new RuleName(newName, newDesc, newJson, newTemp, newSql, newPart);
        updatedRuleName.setId(id);
        
        when(ruleNameRepository.findById(id)).thenReturn(java.util.Optional.of(existingRuleName));

        // When
        ruleNameService.update(updatedRuleName);

        // Then
        ArgumentCaptor<RuleName> captor = ArgumentCaptor.forClass(RuleName.class);
        verify(ruleNameRepository).save(captor.capture());
        RuleName savedRuleName = captor.getValue();
        
        assertEquals(newName, savedRuleName.getName());
        assertEquals(newDesc, savedRuleName.getDescription());
        assertEquals(newJson, savedRuleName.getJson());
        assertEquals(newTemp, savedRuleName.getTemplate());
        assertEquals(newSql, savedRuleName.getSqlStr());
        assertEquals(newPart, savedRuleName.getSqlPart());
        assertEquals(id, savedRuleName.getId());
    }

    @Test
    public void testUpdateRuleNameShouldThrowExceptionWhenRuleNameNotFound() {
        // Given
        RuleName ruleNameToUpdate = new RuleName("Rule1", "Desc1", "json1", "temp1", "sql1", "part1");
        ruleNameToUpdate.setId(999);
        
        when(ruleNameRepository.findById(999)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> ruleNameService.update(ruleNameToUpdate));
    }

}
