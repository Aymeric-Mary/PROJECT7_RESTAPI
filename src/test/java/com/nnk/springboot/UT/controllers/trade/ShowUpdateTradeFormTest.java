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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowUpdateTradeFormTest {

    @InjectMocks
    private TradeController tradeController;

    @Mock
    private TradeService tradeServiceMock;

    @Test
    void testShowUpdateFormWhenTradeExist() {
        // Given
        Model model = new ExtendedModelMap();
        Trade trade = new Trade();
        when(tradeServiceMock.findById(1234)).thenReturn(Optional.of(trade));
        // When
        String viewName = tradeController.showUpdateForm(1234, model);
        // Then
        assertThat(model.getAttribute("trade")).isEqualTo(trade);
        assertThat(viewName).isEqualTo("trade/update");
    }

    @Test
    void testShowUpdateFormWhenTradeDontExist() {
        // Given
        Model model = new ExtendedModelMap();
        when(tradeServiceMock.findById(1234)).thenReturn(Optional.empty());
        // When
        String viewName = tradeController.showUpdateForm(1234, model);
        // Then
        assertThat(viewName).isEqualTo("redirect:/trade/list");
    }
}
