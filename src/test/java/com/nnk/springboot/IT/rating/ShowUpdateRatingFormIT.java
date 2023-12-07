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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ShowUpdateRatingFormIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    @WithMockPrincipal
    void testShowUpdateFormWhenRatingExist() throws Exception {
        // Given
        Rating rating = Rating.builder()
                .moodysRating("moodysRating")
                .sandPRating("sandPRating")
                .fitchRating("fitchRating")
                .orderNumber(10)
                .build();
        ratingRepository.save(rating);
        // When
        mockMvc.perform(get("/rating/update/" + rating.getId()))
                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().attribute("rating", rating));
    }

    @Test
    @WithMockPrincipal
    void testShowUpdateFormWhenRatingNotExist() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/rating/update/1234"))
                // Then
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"));
    }

}
