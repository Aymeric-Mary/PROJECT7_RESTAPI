package com.nnk.springboot.IT.rating;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.mock.WithMockPrincipal;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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
class ValidateRatingIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    @BeforeEach
    void setUp() {
        ratingRepository.deleteAll();
    }

    @Test
    @WithMockPrincipal
    void validateRating_whenParametersAreGood() throws Exception {
        // Given
        // When
        mockMvc.perform(post("/rating/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"));
        // Then
        assertThat(ratingRepository.findAll())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(
                        Rating.builder()
                                .moodysRating("moodysRating")
                                .sandPRating("sandPRating")
                                .fitchRating("fitchRating")
                                .build()
                );
    }
}
