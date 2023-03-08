package de.muehlbauer.assignment.order.mapper;

import de.muehlbauer.assignment.order.api.dto.OrderItemDto;
import de.muehlbauer.assignment.order.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "order",
            ignore = true )
    OrderItemEntity toEntity(OrderItemDto dto);

}
