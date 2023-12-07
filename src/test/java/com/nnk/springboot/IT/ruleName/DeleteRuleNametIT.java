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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DeleteRuleNametIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    @WithMockPrincipal
    void testRuleNameDelete() throws Exception {
        // Given
        RuleName ruleName = RuleName.builder()
                .name("name")
                .description("description")
                .json("json")
                .template("template")
                .sqlStr("sqlStr")
                .sqlPart("sqlPart")
                .build();
        ruleNameRepository.save(ruleName);
        // When
        mockMvc.perform(get("/ruleName/delete/" + ruleName.getId()))
                // Then
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/ruleName/list"));
        assertThat(ruleNameRepository.findById(ruleName.getId())).isEmpty();
    }

}
