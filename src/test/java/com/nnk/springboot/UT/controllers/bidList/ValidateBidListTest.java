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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidateBidListTest {

    @InjectMocks
    private BidListController bidListController;

    @Mock
    private BidService bidServiceMock;

    @Mock
    private BindingResult bindingResultMock;

    @Test
    void testValidateWhenDontHasErrors() {
        // Given
        BidList bidList = new BidList();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        // When
        String viewName = bidListController.validate(bidList, bindingResultMock);
        // Then
        verify(bidServiceMock, times(1)).create(bidList);
        assertThat(viewName).isEqualTo("redirect:/bidList/list");
    }

    @Test
    void testValidateWhenHasErrors() {
        // Given
        BidList bidList = new BidList();
        when(bindingResultMock.hasErrors()).thenReturn(true);
        // When
        String viewName = bidListController.validate(bidList, bindingResultMock);
        // Then
        verifyNoInteractions(bidServiceMock);
        assertThat(viewName).isEqualTo("bidList/add");
    }
}
