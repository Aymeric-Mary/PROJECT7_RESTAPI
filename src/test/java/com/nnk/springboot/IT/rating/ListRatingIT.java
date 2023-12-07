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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ListRatingIT {

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
    void testRatingList() throws Exception {
        // Given
        Rating rating1 = Rating.builder()
                .moodysRating("moodysRating1")
                .sandPRating("sandPRating1")
                .fitchRating("fitchRating1")
                .orderNumber(10)
                .build();
        Rating rating2 = Rating.builder()
                .moodysRating("moodysRating2")
                .sandPRating("sandPRating2")
                .fitchRating("fitchRating2")
                .orderNumber(20)
                .build();
        ratingRepository.saveAll(List.of(rating1, rating2));
        // When
        mockMvc.perform(get("/rating/list"))
                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings", hasSize(2)))
                .andExpect(model().attribute("ratings",List.of(rating1, rating2)));
    }

}
