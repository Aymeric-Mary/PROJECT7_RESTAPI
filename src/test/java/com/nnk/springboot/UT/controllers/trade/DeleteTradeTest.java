package com.nnk.springboot.UT.controllers.trade;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteTradeTest {

    @InjectMocks
    private TradeController tradeController;

    @Mock
    private TradeService tradeServiceMock;

    @Test
    void testTradeDelete() {
        // Given
        // When
        String viewName = tradeController.deleteTrade(1);
        // Then
        verify(tradeServiceMock).deleteById(1);
        assertThat(viewName).isEqualTo("redirect:/trade/list");
    }
}
