package com.nnk.springboot.UT.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.mapper.RuleNameMapper;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RuleNameTest {

    @Mock
    private RuleNameMapper ruleNameMapperMock;

    @Mock
    private RuleNameRepository ruleNameRepositoryMock;

    @InjectMocks
    private RuleNameService ruleNameService;

    @Test
    void testCreate() {
        // Given
        RuleName ruleName = new RuleName();
        when(ruleNameRepositoryMock.save(ruleName)).thenReturn(ruleName);
        // When
        RuleName result = ruleNameService.create(ruleName);
        // Then
        verify(ruleNameRepositoryMock).save(ruleName);
        assertThat(result).isEqualTo(ruleName);
    }

    @Test
    void testFindAll() {
        // Given
        List<RuleName> ruleNames = List.of(
                new RuleName(),
                new RuleName()
        );
        when(ruleNameRepositoryMock.findAll()).thenReturn(ruleNames);
        // When
        List<RuleName> result = ruleNameService.findAll();
        // Then
        verify(ruleNameRepositoryMock).findAll();
        assertThat(result).isEqualTo(ruleNames);
    }

    @Test
    void testFindById() {
        // Given
        RuleName ruleName = new RuleName();
        when(ruleNameRepositoryMock.findById(1)).thenReturn(Optional.of(ruleName));
        // When
        Optional<RuleName> result = ruleNameService.findById(1);
        // Then
        assertThat(result).isPresent().get().isEqualTo(ruleName);
    }

    @Test
    void testUpdate() {
        // Given
        RuleName existingRuleName = RuleName.builder()
                .id(1)
                .name("name")
                .description("description")
                .json("json")
                .template("template")
                .sqlStr("sqlStr")
                .sqlPart("sqlPart")
                .build();
        RuleName ruleName = RuleName.builder()
                .id(1)
                .name("new name")
                .description("new description")
                .json("new json")
                .template("new template")
                .sqlStr("new sqlStr")
                .sqlPart("new sqlPart")
                .build();
        when(ruleNameRepositoryMock.save(existingRuleName)).thenReturn(existingRuleName);
        doAnswer(invocation -> {
            RuleName existing = invocation.getArgument(0, RuleName.class);
            RuleName updating = invocation.getArgument(1, RuleName.class);
            existing.setName(updating.getName());
            existing.setDescription(updating.getDescription());
            existing.setJson(updating.getJson());
            existing.setTemplate(updating.getTemplate());
            existing.setSqlStr(updating.getSqlStr());
            existing.setSqlPart(updating.getSqlPart());
            return null;
        }).when(ruleNameMapperMock).updateRuleName(existingRuleName, ruleName);
        // When
        try (MockedStatic<DateUtils> dateUtilsMock = mockStatic(DateUtils.class)) {
            dateUtilsMock.when(DateUtils::now).thenReturn(Instant.parse("2021-06-25T10:15:30.00Z"));
            RuleName result = ruleNameService.update(existingRuleName, ruleName);
            // Then
            verify(ruleNameRepositoryMock).save(existingRuleName);
            RuleName expectedRuleName = RuleName
                    .builder()
                    .id(1)
                    .name("new name")
                    .description("new description")
                    .json("new json")
                    .template("new template")
                    .sqlStr("new sqlStr")
                    .sqlPart("new sqlPart")
                    .build();
            assertThat(existingRuleName)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedRuleName);
            assertThat(result).isEqualTo(existingRuleName);
        }
    }

    @Test
    void deleteByIdTest(){
        // Given
        Integer id = 1;
        // When
        ruleNameService.deleteById(id);
        // Then
        verify(ruleNameRepositoryMock).deleteById(id);
    }


}
