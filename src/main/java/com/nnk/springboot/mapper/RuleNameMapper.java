package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RuleNameMapper {

    @Mapping(target = "id", ignore = true)
    void updateRuleName(@MappingTarget RuleName existingRuleName, RuleName ruleName);
}
