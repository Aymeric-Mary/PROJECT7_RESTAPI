package com.nnk.springboot.UT.controllers.trade;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateTradeTest {

    @Mock
    TradeService tradeServiceMock;

    @Mock
    BindingResult bindingResultMock;

    @InjectMocks
    TradeController tradeController;

    @Test
    void testUpdateTradeWhenHasError() {
        // Given
        Trade trade = new Trade();
        when(bindingResultMock.hasErrors()).thenReturn(true);
        // When
        String viewName = tradeController.updateTrade(1234, trade, bindingResultMock);
        // Then
        assertThat(viewName).isEqualTo("trade/update");
    }

    @Test
    void testUpdateTradeWhenNoError() {
        // Given
        Trade trade = new Trade();
        Trade existingTrade = new Trade();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(tradeServiceMock.findById(1234)).thenReturn(Optional.of(existingTrade));
        // When
        String viewName = tradeController.updateTrade(1234, trade, bindingResultMock);
        // Then
        verify(tradeServiceMock).update(existingTrade, trade);
        assertThat(viewName).isEqualTo("redirect:/trade/list");
    }
}
