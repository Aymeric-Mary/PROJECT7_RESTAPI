package com.nnk.springboot.IT.ruleName;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.utils.DateUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
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
class ValidateRuleNameIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    @WithMockPrincipal
    void validateCurvePoint_whenParametersAreGood() throws Exception {
        // Given
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {
            Instant now = Instant.parse("2023-07-27T12:00:00Z");
            mockedDateUtils.when(DateUtils::now).thenReturn(now);
            // When
            mockMvc.perform(post("/ruleName/validate")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("name", "name")
                            .param("description", "description")
                            .param("json", "json")
                            .param("template", "template")
                            .param("sqlStr", "sqlStr")
                            .param("sqlPart", "sqlPart")
                    )
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/ruleName/list"));
            // Then
            assertThat(ruleNameRepository.findAll())
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                    .containsExactly(
                            RuleName.builder()
                                    .name("name")
                                    .description("description")
                                    .json("json")
                                    .template("template")
                                    .sqlStr("sqlStr")
                                    .sqlPart("sqlPart")
                                    .build()
                    );
        }
    }
}
