package com.nnk.springboot.IT.bidList;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ListBidListIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    @WithMockPrincipal
    void testCurvePointList() throws Exception {
        // Given
        BidList bidList1 = BidList.builder()
                .account("account1")
                .type("type1")
                .bidQuantity(10.0)
                .build();
        BidList bidList2 = BidList.builder()
                .account("account2")
                .type("type2")
                .bidQuantity(20.0)
                .build();
        bidListRepository.saveAll(List.of(bidList1, bidList2));
        // When
        mockMvc.perform(get("/bidList/list"))
                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(model().attribute("bidLists", hasSize(2)))
                .andExpect(model().attribute("bidLists",List.of(bidList1, bidList2)));
    }

}
