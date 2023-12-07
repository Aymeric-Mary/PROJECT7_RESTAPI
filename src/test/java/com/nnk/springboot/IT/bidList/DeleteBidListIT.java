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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DeleteBidListIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    @WithMockPrincipal
    void testBidListDelete() throws Exception {
        // Given
        BidList bidList = BidList.builder()
                .account("account")
                .type("type")
                .bidQuantity(10.0)
                .build();
        bidListRepository.save(bidList);
        // When
        mockMvc.perform(get("/bidList/delete/" + bidList.getId()))
                // Then
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"));
        assertThat(bidListRepository.findById(bidList.getId())).isEmpty();
    }

}
