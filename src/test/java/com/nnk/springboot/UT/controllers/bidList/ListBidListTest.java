package com.nnk.springboot.UT.controllers.bidList;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.BidService;
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
public class ListBidListTest {

    @InjectMocks
    private BidListController bidListController;

    @Mock
    private BidService bidServiceMock;

    @Test
    public void testCurvePointList() {
        // Given
        Model model = new ExtendedModelMap();
        List<BidList> bids = List.of(
                new BidList(),
                new BidList()
        );
        when(bidServiceMock.findAll()).thenReturn(bids);
        // When
        String viewName = bidListController.home(model);
        // Then
        assertThat(model.getAttribute("bidLists")).isEqualTo(bids);
        assertThat(viewName).isEqualTo("bidList/list");
    }
}
