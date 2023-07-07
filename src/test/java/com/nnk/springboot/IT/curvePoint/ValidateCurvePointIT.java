package com.nnk.springboot.IT.curvePoint;

import com.nnk.springboot.ItTools;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ValidateCurvePointIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    @WithMockPrincipal
    void validateCurvePoint_whenParametersAreGood() throws Exception {
        // Given
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {
            Instant now = Instant.parse("2023-07-27T12:00:00Z");
            mockedDateUtils.when(DateUtils::now).thenReturn(now);
            // When
            mockMvc.perform(post("/curvePoint/validate")
                            .param("curveId", "10")
                            .param("term", "12.0")
                            .param("value", "5.5")
                    )
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/curvePoint/list"));
            // Then
            assertThat(curvePointRepository.findAll())
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                    .containsExactly(
                            CurvePoint.builder()
                                    .creationDate(Timestamp.from(now))
                                    .asOfDate(Timestamp.from(now))
                                    .curveId(10)
                                    .term(12.0)
                                    .value(5.5)
                                    .build()
                    );
        }
    }

    @WithMockPrincipal
    @ParameterizedTest
    @CsvSource({
            "0,0,0"
    })
    void validateCurvePoint_whenParametersAreBad(String curveId, String term, String value) throws Exception {
        // Given
        // When
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", curveId)
                        .param("term", term)
                        .param("value", value)
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"))
                .andExpect(forwardedUrl("curvePoint/add"));
        // Then
    }
}
