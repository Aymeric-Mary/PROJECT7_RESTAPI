package com.nnk.springboot.UT.controllers.rating;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateRatingTest {

    @Mock
    RatingService ratingServiceMock;

    @Mock
    BindingResult bindingResultMock;

    @InjectMocks
    RatingController ratingController;

    @Test
    void testUpdateRatingWhenHasError() {
        // Given
        Rating rating = new Rating();
        when(bindingResultMock.hasErrors()).thenReturn(true);
        // When
        String viewName = ratingController.updateRating(1234, rating, bindingResultMock);
        // Then
        assertThat(viewName).isEqualTo("rating/update");
    }

    @Test
    void testUpdateRatingWhenNoError() {
        // Given
        Rating rating = new Rating();
        Rating existingRating = new Rating();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(ratingServiceMock.findById(1234)).thenReturn(Optional.of(existingRating));
        // When
        String viewName = ratingController.updateRating(1234, rating, bindingResultMock);
        // Then
        verify(ratingServiceMock).update(existingRating, rating);
        assertThat(viewName).isEqualTo("redirect:/rating/list");
    }
}
