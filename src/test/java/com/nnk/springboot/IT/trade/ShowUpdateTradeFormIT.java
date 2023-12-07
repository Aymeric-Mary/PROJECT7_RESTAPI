package com.nnk.springboot.IT.trade;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ShowUpdateTradeFormIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    @WithMockPrincipal
    void testShowUpdateFormWhenTradeExist() throws Exception {
        // Given
        Trade trade = Trade.builder()
                .account("Account")
                .type("Type")
                .buyQuantity(10.0)
                .build();
        tradeRepository.save(trade);
        // When
        mockMvc.perform(get("/trade/update/" + trade.getId()))
                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().attribute("trade", trade));
    }

    @Test
    @WithMockPrincipal
    void testShowUpdateFormWhenTradeNotExist() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/trade/update/1234"))
                // Then
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/trade/list"));
    }

}
