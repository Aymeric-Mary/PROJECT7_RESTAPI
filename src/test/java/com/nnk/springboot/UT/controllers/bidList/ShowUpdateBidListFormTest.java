package com.nnk.springboot.UT.controllers.bidList;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidService;
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
class ShowUpdateBidListFormTest {

    @InjectMocks
    private BidListController bidListController;

    @Mock
    private BidService bidServiceMock;

    @Test
    void testShowUpdateFormWhenBidListExist() {
        // Given
        Model model = new ExtendedModelMap();
        BidList bidList = new BidList();
        when(bidServiceMock.findById(1234)).thenReturn(Optional.of(bidList));
        // When
        String viewName = bidListController.showUpdateForm(1234, model);
        // Then
        assertThat(model.getAttribute("bidList")).isEqualTo(bidList);
        assertThat(viewName).isEqualTo("bidList/update");
    }

    @Test
    void testShowUpdateFormWhenBidListDontExist() {
        // Given
        Model model = new ExtendedModelMap();
        when(bidServiceMock.findById(1234)).thenReturn(Optional.empty());
        // When
        String viewName = bidListController.showUpdateForm(1234, model);
        // Then
        assertThat(viewName).isEqualTo("redirect:/bidList/list");
    }
}
