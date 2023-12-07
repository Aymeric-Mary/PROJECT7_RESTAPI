package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(target = "id", ignore = true)
    void updateRating(@MappingTarget Rating existingRating, Rating rating);
}
