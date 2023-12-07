package com.nnk.springboot.UT.controllers.bidList;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.services.BidService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteBidListTest {

    @InjectMocks
    private BidListController bidListController;

    @Mock
    private BidService bidListServiceMock;

    @Test
    void testBidListDelete() {
        // Given
        // When
        String viewName = bidListController.deleteBid(1);
        // Then
        verify(bidListServiceMock).deleteById(1);
        assertThat(viewName).isEqualTo("redirect:/bidList/list");
    }
}
