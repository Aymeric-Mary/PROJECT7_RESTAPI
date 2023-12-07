package com.nnk.springboot.IT.trade;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ValidateTradeIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    @WithMockPrincipal
    void validateTrade_whenParametersAreGood() throws Exception {
        // Given
            // When
            mockMvc.perform(post("/trade/validate")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("account", "Account")
                            .param("type", "Type")
                            .param("buyQuantity", "10.0")
                    )
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/trade/list"));
            // Then
            assertThat(tradeRepository.findAll())
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                    .containsExactly(
                            Trade.builder()
                                    .account("Account")
                                    .type("Type")
                                    .buyQuantity(10.0)
                                    .build()
                    );
    }
}
