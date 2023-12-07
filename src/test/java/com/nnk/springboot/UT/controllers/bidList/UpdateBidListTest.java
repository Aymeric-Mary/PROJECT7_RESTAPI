package com.nnk.springboot.UT.controllers.bidList;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidService;
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
class UpdateBidListTest {

    @Mock
    BidService bidServiceMock;

    @Mock
    BindingResult bindingResultMock;

    @InjectMocks
    BidListController bidListController;

    @Test
    void testUpdateBidListWhenHasError() {
        // Given
        BidList bidList = new BidList();
        when(bindingResultMock.hasErrors()).thenReturn(true);
        // When
        String viewName = bidListController.updateBid(1234, bidList, bindingResultMock);
        // Then
        assertThat(viewName).isEqualTo("bidList/update");
    }

    @Test
    void testUpdateBidListWhenNoError() {
        // Given
        BidList bidList = new BidList();
        BidList existingBidList = new BidList();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(bidServiceMock.findById(1234)).thenReturn(Optional.of(existingBidList));
        // When
        String viewName = bidListController.updateBid(1234, bidList, bindingResultMock);
        // Then
        verify(bidServiceMock).update(existingBidList, bidList);
        assertThat(viewName).isEqualTo("redirect:/bidList/list");
    }
}
