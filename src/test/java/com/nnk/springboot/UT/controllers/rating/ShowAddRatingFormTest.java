package com.nnk.springboot.UT.controllers.rating;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ShowAddRatingFormTest {

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private RatingService ratingServiceMock;

    @Test
    void testShowAddForm() {
        // Given
        Rating rating = new Rating();
        // When
        String viewName = ratingController.addRatingForm(rating);
        // Then
        assertThat(viewName).isEqualTo("rating/add");
    }
}
