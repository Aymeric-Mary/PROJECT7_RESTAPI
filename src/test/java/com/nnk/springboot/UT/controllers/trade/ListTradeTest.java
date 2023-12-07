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
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListTradeTest {

    @InjectMocks
    private TradeController tradeController;

    @Mock
    private TradeService tradeServiceMock;

    @Test
    void testCurvePointList() {
        // Given
        Model model = new ExtendedModelMap();
        List<Trade> trades = List.of(
                new Trade(),
                new Trade()
        );
        when(tradeServiceMock.findAll()).thenReturn(trades);
        // When
        String viewName = tradeController.home(model);
        // Then
        assertThat(model.getAttribute("trades")).isEqualTo(trades);
        assertThat(viewName).isEqualTo("trade/list");
    }
}
