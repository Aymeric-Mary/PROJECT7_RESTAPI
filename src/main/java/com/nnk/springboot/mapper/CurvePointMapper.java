package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.CurvePoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CurvePointMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    void updateCurvePoint(@MappingTarget CurvePoint existingCurvePoint, CurvePoint curvePoint);
}
