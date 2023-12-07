package com.nnk.springboot.IT.bidList;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.utils.DateUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UpdateBidListIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    @WithMockPrincipal
    void validateBidList_whenParametersAreGood() throws Exception {
        // Given
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {
            BidList existingBidList = BidList.builder()
                    .creationDate(Instant.parse("2021-07-27T12:00:00Z"))
                    .account("account")
                    .type("type")
                    .bidQuantity(10.0)
                    .build();
            bidListRepository.save(existingBidList);
            Instant now = Instant.parse("2023-07-27T12:00:00Z");
            mockedDateUtils.when(DateUtils::now).thenReturn(now);
            // When
            mockMvc.perform(post("/bidList/update/" + existingBidList.getId())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("account", "new account")
                            .param("type", "new type")
                            .param("bidQuantity", "5.0")
                    )
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/bidList/list"));
            // Then
            assertThat(bidListRepository.findAll())
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                    .containsExactly(
                            BidList.builder()
                                    .id(1)
                                    .creationDate(Instant.parse("2021-07-27T12:00:00Z"))
                                    .revisionDate(now)
                                    .account("new account")
                                    .type("new type")
                                    .bidQuantity(5.0)
                                    .build()
                    );
        }
    }

    @WithMockPrincipal
    @Test
    void validateBidList_whenParametersAreBad() throws Exception {
        // Given
        // When
        mockMvc.perform(post("/bidList/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "")
                        .param("type", "")
                )
                // Then
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("bidList", "account", "type"))
                .andExpect(view().name("bidList/update"));
    }

}
