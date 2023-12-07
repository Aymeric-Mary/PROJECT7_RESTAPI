package com.nnk.springboot.UT.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingMapper ratingMapperMock;

    @Mock
    private RatingRepository ratingRepositoryMock;

    @InjectMocks
    private RatingService ratingService;

    @Test
    void testCreate() {
        // Given
        Rating rating = new Rating();
        when(ratingRepositoryMock.save(rating)).thenReturn(rating);
        // When
        Rating result = ratingService.create(rating);
        // Then
        verify(ratingRepositoryMock).save(rating);
        assertThat(result).isEqualTo(rating);
    }

    @Test
    void testFindAll() {
        // Given
        List<Rating> ratings = List.of(
                new Rating(),
                new Rating()
        );
        when(ratingRepositoryMock.findAll()).thenReturn(ratings);
        // When
        List<Rating> result = ratingService.findAll();
        // Then
        verify(ratingRepositoryMock).findAll();
        assertThat(result).isEqualTo(ratings);
    }

    @Test
    void testFindById() {
        // Given
        Rating rating = new Rating();
        when(ratingRepositoryMock.findById(1)).thenReturn(Optional.of(rating));
        // When
        Optional<Rating> result = ratingService.findById(1);
        // Then
        assertThat(result).isPresent().get().isEqualTo(rating);
    }

    @Test
    void testUpdate() {
        // Given
        Rating existingRating = Rating.builder()
                .id(1)
                .moodysRating("moodysRating")
                .sandPRating("sandPRating")
                .fitchRating("fitchRating")
                .orderNumber(10)
                .build();
        Rating rating = Rating.builder()
                .id(1)
                .moodysRating("new moodysRating")
                .sandPRating("new sandPRating")
                .fitchRating("new fitchRating")
                .orderNumber(20)
                .build();
        when(ratingRepositoryMock.save(existingRating)).thenReturn(existingRating);
        doAnswer(invocation -> {
            Rating existing = invocation.getArgument(0, Rating.class);
            Rating updating = invocation.getArgument(1, Rating.class);
            existing.setMoodysRating(updating.getMoodysRating());
            existing.setSandPRating(updating.getSandPRating());
            existing.setFitchRating(updating.getFitchRating());
            existing.setOrderNumber(updating.getOrderNumber());
            return null;
        }).when(ratingMapperMock).updateRating(existingRating, rating);
        // When
        try (MockedStatic<DateUtils> dateUtilsMock = mockStatic(DateUtils.class)) {
            dateUtilsMock.when(DateUtils::now).thenReturn(Instant.parse("2021-06-25T10:15:30.00Z"));
            Rating result = ratingService.update(existingRating, rating);
            // Then
            verify(ratingRepositoryMock).save(existingRating);
            Rating expectedRating = Rating
                    .builder()
                    .id(1)
                    .moodysRating("new moodysRating")
                    .sandPRating("new sandPRating")
                    .fitchRating("new fitchRating")
                    .orderNumber(20)
                    .build();
            assertThat(existingRating)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedRating);
            assertThat(result).isEqualTo(existingRating);
        }
    }

    @Test
    void deleteByIdTest(){
        // Given
        Integer id = 1;
        // When
        ratingService.deleteById(id);
        // Then
        verify(ratingRepositoryMock).deleteById(id);
    }


}
