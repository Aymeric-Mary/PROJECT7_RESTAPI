package com.nnk.springboot.UT.controllers.rating;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidateRatingTest {

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private RatingService ratingServiceMock;

    @Mock
    private BindingResult bindingResultMock;

    @Test
    public void testValidateWhenDontHasErrors() {
        // Given
        Rating rating = new Rating();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        // When
        String viewName = ratingController.validate(rating, bindingResultMock, new ExtendedModelMap());
        // Then
        verify(ratingServiceMock, times(1)).create(rating);
        assertThat(viewName).isEqualTo("redirect:/rating/list");
    }

    @Test
    public void testValidateWhenHasErrors() {
        // Given
        Rating rating = new Rating();
        when(bindingResultMock.hasErrors()).thenReturn(true);
        // When
        String viewName = ratingController.validate(rating, bindingResultMock, new ExtendedModelMap());
        // Then
        verifyNoInteractions(ratingServiceMock);
        assertThat(viewName).isEqualTo("rating/add");
    }
}
