package com.nnk.springboot.IT.trade;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.utils.DateUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UpdateTradeIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    @WithMockPrincipal
    void validateTrade_whenParametersAreGood() throws Exception {
        // Given
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {
            Trade existingTrade = Trade.builder()
                    .creationDate(Timestamp.from(Instant.parse("2021-07-27T12:00:00Z")))
                    .account("Account")
                    .type("Type")
                    .buyQuantity(10.0)
                    .build();
            tradeRepository.save(existingTrade);
            Instant now = Instant.parse("2023-07-27T12:00:00Z");
            mockedDateUtils.when(DateUtils::now).thenReturn(now);
            // When
            mockMvc.perform(post("/trade/update/" + existingTrade.getId())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("account", "New Account")
                            .param("type", "New Type")
                            .param("buyQuantity", "5.0")
                    )
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/trade/list"));
            // Then
            Optional<Trade> actualTrade = tradeRepository.findById(existingTrade.getId());
            assertThat(actualTrade).isPresent();
            Trade expectedTrade = Trade.builder()
                    .id(existingTrade.getId())
                    .creationDate(existingTrade.getCreationDate())
                    .revisionDate(Timestamp.from(now))
                    .account("New Account")
                    .type("New Type")
                    .buyQuantity(5.0)
                    .build();
            assertThat(actualTrade.get())
                    .usingRecursiveComparison()
                    .isEqualTo(expectedTrade);
        }
    }

}
