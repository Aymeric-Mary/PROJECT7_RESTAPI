package com.nnk.springboot.UT.controllers.trade;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TradFormTest {

    @InjectMocks
    private TradeController tradController;

    @Mock
    private TradeService tradServiceMock;

    @Test
    void testShowAddForm() {
        // Given
        Trade trade = new Trade();
        // When
        String viewName = tradController.addTradeForm(trade);
        // Then
        assertThat(viewName).isEqualTo("trade/add");
    }
}
