package com.nnk.springboot.UT.controllers.rating;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowUpdateRatingFormTest {

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private RatingService ratingServiceMock;

    @Test
    void testShowUpdateFormWhenRatingExist() {
        // Given
        Model model = new ExtendedModelMap();
        Rating rating = new Rating();
        when(ratingServiceMock.findById(1234)).thenReturn(Optional.of(rating));
        // When
        String viewName = ratingController.showUpdateForm(1234, model);
        // Then
        assertThat(model.getAttribute("rating")).isEqualTo(rating);
        assertThat(viewName).isEqualTo("rating/update");
    }

    @Test
    void testShowUpdateFormWhenRatingDontExist() {
        // Given
        Model model = new ExtendedModelMap();
        when(ratingServiceMock.findById(1234)).thenReturn(Optional.empty());
        // When
        String viewName = ratingController.showUpdateForm(1234, model);
        // Then
        assertThat(viewName).isEqualTo("redirect:/rating/list");
    }
}
