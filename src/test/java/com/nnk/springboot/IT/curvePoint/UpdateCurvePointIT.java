package com.nnk.springboot.IT.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.utils.DateUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UpdateCurvePointIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    void setUp() {
        curvePointRepository.deleteAll();
    }

    @Test
    @WithMockPrincipal
    void validateCurvePoint_whenParametersAreGood() throws Exception {
        // Given
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {
            CurvePoint existingCurvePoint = CurvePoint.builder()
                    .creationDate(Instant.parse("2021-07-27T12:00:00Z"))
                    .asOfDate(Instant.parse("2021-07-27T12:00:00Z"))
                    .curveId(1)
                    .term(10.0)
                    .value(15.0)
                    .build();
            curvePointRepository.save(existingCurvePoint);
            Instant now = Instant.parse("2023-07-27T12:00:00Z");
            mockedDateUtils.when(DateUtils::now).thenReturn(now);
            // When
            mockMvc.perform(post("/curvePoint/update/" + existingCurvePoint.getId())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
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
                                    .id(1)
                                    .creationDate(Instant.parse("2021-07-27T12:00:00Z"))
                                    .asOfDate(now)
                                    .curveId(10)
                                    .term(12.0)
                                    .value(5.5)
                                    .build()
                    );
        }
    }

    @Test
    @WithMockPrincipal
    void validateCurvePoint_whenParametersAreBad() throws Exception {
        // Given
        // When
        mockMvc.perform(post("/curvePoint/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curveId", "0")
                        .param("term", "-1.0")
                        .param("value", "-1.0")
                )
                // Then
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId", "term", "value"))
                .andExpect(view().name("curvePoint/update"));
    }

}
