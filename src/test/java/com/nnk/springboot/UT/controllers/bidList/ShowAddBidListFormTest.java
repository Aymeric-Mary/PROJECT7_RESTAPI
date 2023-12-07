package com.nnk.springboot.UT.controllers.bidList;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ShowAddBidListFormTest {

    @InjectMocks
    private BidListController bidListController;

    @Mock
    private BidService bidServiceMock;

    @Test
    void testShowAddForm() {
        // Given
        BidList bidList = new BidList();
        // When
        String viewName = bidListController.addBidForm(bidList);
        // Then
        assertThat(viewName).isEqualTo("bidList/add");
    }
}
