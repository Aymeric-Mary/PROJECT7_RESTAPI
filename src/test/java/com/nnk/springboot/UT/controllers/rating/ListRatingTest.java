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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListRatingTest {

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private RatingService ratingServiceMock;

    @Test
    void testCurvePointList() {
        // Given
        Model model = new ExtendedModelMap();
        List<Rating> ratings = List.of(
                new Rating(),
                new Rating()
        );
        when(ratingServiceMock.findAll()).thenReturn(ratings);
        // When
        String viewName = ratingController.home(model);
        // Then
        assertThat(model.getAttribute("ratings")).isEqualTo(ratings);
        assertThat(viewName).isEqualTo("rating/list");
    }
}
