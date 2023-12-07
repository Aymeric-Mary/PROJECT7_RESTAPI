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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ListTradeIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    @WithMockPrincipal
    void testTradeList() throws Exception {
        // Given
        Trade trade1 = Trade.builder()
                .account("Account1")
                .type("Type1")
                .buyQuantity(10.0)
                .build();
        Trade trade2 = Trade.builder()
                .account("Account2")
                .type("Type2")
                .buyQuantity(20.0)
                .build();
        tradeRepository.saveAll(List.of(trade1, trade2));
        // When
        mockMvc.perform(get("/trade/list"))
                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().attribute("trades", hasSize(2)))
                .andExpect(model().attribute("trades",List.of(trade1, trade2)));
    }

}
