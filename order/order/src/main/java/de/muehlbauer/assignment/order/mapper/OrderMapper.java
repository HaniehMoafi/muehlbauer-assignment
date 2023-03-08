package de.muehlbauer.assignment.order.mapper;

import de.muehlbauer.assignment.order.api.dto.OrderDto;
import de.muehlbauer.assignment.order.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto (OrderEntity entity);


    @Mapping(target = "items" , ignore = true)
    @Mapping(target = "orderNumber" , ignore = true)
    OrderEntity toEntity(OrderDto dto);

}
