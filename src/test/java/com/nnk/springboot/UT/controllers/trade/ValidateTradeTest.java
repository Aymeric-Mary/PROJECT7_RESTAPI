package com.nnk.springboot.UT.controllers.trade;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidateTradeTest {

    @InjectMocks
    private TradeController tradeController;

    @Mock
    private TradeService tradeServiceMock;

    @Mock
    private BindingResult bindingResultMock;

    @Test
    void testValidateWhenDontHasErrors() {
        // Given
        Trade trade = new Trade();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        // When
        String viewName = tradeController.validate(trade, bindingResultMock, new ExtendedModelMap());
        // Then
        verify(tradeServiceMock, times(1)).create(trade);
        assertThat(viewName).isEqualTo("redirect:/trade/list");
    }

    @Test
    void testValidateWhenHasErrors() {
        // Given
        Trade trade = new Trade();
        when(bindingResultMock.hasErrors()).thenReturn(true);
        // When
        String viewName = tradeController.validate(trade, bindingResultMock, new ExtendedModelMap());
        // Then
        verifyNoInteractions(tradeServiceMock);
        assertThat(viewName).isEqualTo("trade/add");
    }
}
