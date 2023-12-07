package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.Trade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TradeMapper {

    @Mapping(target = "id", ignore = true)
    void updateTrade(@MappingTarget Trade existingTrade, Trade trade);
}
