package com.nnk.springboot.IT.ruleName;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ListRuleNameIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    @WithMockPrincipal
    void testRuleNameList() throws Exception {
        // Given
        RuleName ruleName1 = RuleName.builder()
                .name("name1")
                .description("description1")
                .json("json1")
                .template("template1")
                .sqlStr("sqlStr1")
                .sqlPart("sqlPart1")
                .build();
        RuleName ruleName2 = RuleName.builder()
                .name("name2")
                .description("description2")
                .json("json2")
                .template("template2")
                .sqlStr("sqlStr2")
                .sqlPart("sqlPart2")
                .build();
        ruleNameRepository.saveAll(List.of(ruleName1, ruleName2));
        // When
        mockMvc.perform(get("/ruleName/list"))
                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().attribute("ruleNames", hasSize(2)))
                .andExpect(model().attribute("ruleNames",List.of(ruleName1, ruleName2)));
    }

}
