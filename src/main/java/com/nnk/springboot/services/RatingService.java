package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    public Rating create(Rating rating) {
        return ratingRepository.save(rating);
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> findById(Integer id) {
        return ratingRepository.findById(id);
    }

    public Rating update(Rating existingRating, Rating rating) {
        ratingMapper.updateRating(existingRating, rating);
        return ratingRepository.save(existingRating);
    }

    public void deleteById(Integer id) {
        ratingRepository.deleteById(id);
    }
}
