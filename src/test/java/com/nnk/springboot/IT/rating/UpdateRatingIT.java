package com.nnk.springboot.IT.rating;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UpdateRatingIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    @WithMockPrincipal
    void updateRating_whenParametersAreGood() throws Exception {
        // Given
        Rating existingRating = Rating.builder()
                .moodysRating("moodysRating")
                .sandPRating("sandPRating")
                .fitchRating("fitchRating")
                .orderNumber(10)
                .build();
        ratingRepository.save(existingRating);
        // When
        mockMvc.perform(post("/rating/update/" + existingRating.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("moodysRating", "new moodysRating")
                        .param("sandPRating", "new sandPRating")
                        .param("fitchRating", "new fitchRating")
                        .param("orderNumber", "5")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"));
        // Then
        assertThat(ratingRepository.findAll())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(
                        Rating.builder()
                                .id(existingRating.getId())
                                .moodysRating("new moodysRating")
                                .sandPRating("new sandPRating")
                                .fitchRating("new fitchRating")
                                .orderNumber(5)
                                .build()
                );
    }

}
