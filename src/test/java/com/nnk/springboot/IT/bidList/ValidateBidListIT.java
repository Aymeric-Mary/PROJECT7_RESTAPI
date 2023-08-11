package com.nnk.springboot.IT.bidList;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class ValidateBidListIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    @WithMockPrincipal
    void validateCurvePoint_whenParametersAreGood() throws Exception {
        // Given
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {
            Instant now = Instant.parse("2023-07-27T12:00:00Z");
            mockedDateUtils.when(DateUtils::now).thenReturn(now);
            // When
            mockMvc.perform(post("/bidList/validate")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("account", "accountName")
                            .param("type", "type")
                            .param("bidQuantity", "10.0")
                    )
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/bidList/list"));
            // Then
            assertThat(bidListRepository.findAll())
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                    .containsExactly(
                            BidList.builder()
                                    .creationDate(now)
                                    .account("accountName")
                                    .type("type")
                                    .bidQuantity(10.0)
                                    .build()
                    );
        }
    }

    @WithMockPrincipal
    @Test
    void validateCurvePoint_whenParametersAreBad() throws Exception {
        // Given
        // When
        mockMvc.perform(post("/bidList/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", "string")
                )
                // Then
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("bidList", "account", "type", "bidQuantity"))
                .andExpect(view().name("bidList/add"));
    }
}
