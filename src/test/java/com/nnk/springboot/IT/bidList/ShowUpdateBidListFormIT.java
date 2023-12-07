package com.nnk.springboot.IT.bidList;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ShowUpdateBidListFormIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    @WithMockPrincipal
    void testShowUpdateFormWhenBidListExist() throws Exception {
        // Given
        BidList bidList = BidList.builder()
                .account("account")
                .type("type")
                .bidQuantity(10.0)
                .build();
        bidListRepository.save(bidList);
        // When
        mockMvc.perform(get("/bidList/update/" + bidList.getId()))
                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(model().attribute("bidList", bidList));
    }

    @Test
    @WithMockPrincipal
    void testShowUpdateFormWhenBidListNotExist() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/bidList/update/1234"))
                // Then
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"));
    }

}
