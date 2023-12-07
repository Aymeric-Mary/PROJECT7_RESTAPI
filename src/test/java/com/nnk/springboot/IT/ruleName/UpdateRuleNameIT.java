package com.nnk.springboot.IT.ruleName;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UpdateRuleNameIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    @WithMockPrincipal
    void validateRuleName_whenParametersAreGood() throws Exception {
        // Given
            RuleName existingRuleName = RuleName.builder()
                    .name("name")
                    .description("description")
                    .json("json")
                    .template("template")
                    .sqlStr("sqlStr")
                    .sqlPart("sqlPart")
                    .build();
            ruleNameRepository.save(existingRuleName);
            Instant now = Instant.parse("2023-07-27T12:00:00Z");
            // When
            mockMvc.perform(post("/ruleName/update/" + existingRuleName.getId())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("name", "new name")
                            .param("description", "new description")
                            .param("json", "new json")
                            .param("template", "new template")
                            .param("sqlStr", "new sqlStr")
                            .param("sqlPart", "new sqlPart")
                    )
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/ruleName/list"));
            // Then
            assertThat(ruleNameRepository.findAll())
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactly(
                            RuleName.builder()
                                    .id(existingRuleName.getId())
                                    .name("new name")
                                    .description("new description")
                                    .json("new json")
                                    .template("new template")
                                    .sqlStr("new sqlStr")
                                    .sqlPart("new sqlPart")
                                    .build()
                    );
    }
}
