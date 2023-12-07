package com.nnk.springboot.IT.rating;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.RatingRepository;
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
class DeleteRatingIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    @WithMockPrincipal
    void testRatingDelete() throws Exception {
        // Given
        Rating rating = Rating.builder()
                .moodysRating("moodysRating")
                .sandPRating("sandPRating")
                .fitchRating("fitchRating")
                .orderNumber(10)
                .build();
        ratingRepository.save(rating);
        // When
        mockMvc.perform(get("/rating/delete/" + rating.getId()))
                // Then
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"));
        assertThat(ratingRepository.findById(rating.getId())).isEmpty();
    }

}
