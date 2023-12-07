package com.nnk.springboot.UT.controllers.rating;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteRatingTest {

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private RatingService ratingServiceMock;

    @Test
    void testRatingDelete() {
        // Given
        // When
        String viewName = ratingController.deleteRating(1);
        // Then
        verify(ratingServiceMock).deleteById(1);
        assertThat(viewName).isEqualTo("redirect:/rating/list");
    }
}
