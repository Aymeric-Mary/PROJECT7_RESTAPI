package com.nnk.springboot.UT.controllers.bidList;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidService;
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
public class ValidateBidListTest {

    @InjectMocks
    private BidListController bidListController;

    @Mock
    private BidService bidServiceMock;

    @Mock
    private BindingResult bindingResultMock;

    @Test
    public void testValidateWhenDontHasErrors() {
        // Given
        BidList bidList = new BidList();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        // When
        String viewName = bidListController.validate(bidList, bindingResultMock, new ExtendedModelMap());
        // Then
        verify(bidServiceMock, times(1)).create(bidList);
        assertThat(viewName).isEqualTo("redirect:/bidList/list");
    }

    @Test
    public void testValidateWhenHasErrors() {
        // Given
        BidList bidList = new BidList();
        when(bindingResultMock.hasErrors()).thenReturn(true);
        // When
        String viewName = bidListController.validate(bidList, bindingResultMock, new ExtendedModelMap());
        // Then
        verifyNoInteractions(bidServiceMock);
        assertThat(viewName).isEqualTo("bidList/add");
    }
}
