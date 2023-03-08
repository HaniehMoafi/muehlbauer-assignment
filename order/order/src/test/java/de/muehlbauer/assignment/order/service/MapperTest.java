package de.muehlbauer.assignment.order.service;

import de.muehlbauer.assignment.order.api.dto.OrderDto;
import de.muehlbauer.assignment.order.api.dto.OrderItemDto;
import de.muehlbauer.assignment.order.api.enumeration.*;
import de.muehlbauer.assignment.order.entity.OrderEntity;
import de.muehlbauer.assignment.order.entity.OrderItemEntity;
import de.muehlbauer.assignment.order.mapper.OrderItemMapper;
import de.muehlbauer.assignment.order.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MapperTest {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void orderItemDto_to_orderItemEntity_map_test() {
        OrderItemDto dto = new OrderItemDto();
        dto.setCoffeeSize(CoffeeSize.LARGE);
        dto.setCount(2);
        dto.setCoffeeType(CoffeeType.ESPRESSO);
        dto.setPrice(new BigDecimal(2));
        dto.setMilkType(MilkType.NONE);
        OrderItemEntity entity = orderItemMapper.toEntity(dto);
        assertEquals(entity.getCoffeeSize(), dto.getCoffeeSize());
        assertTrue(Objects.nonNull(entity.getMilkType()));

    }

    @Test
    void orderDto_to_orderEntity_test() {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setCoffeeSize(CoffeeSize.LARGE);
        orderItemEntity.setCount(2);
        orderItemEntity.setCoffeeType(CoffeeType.ESPRESSO);
        orderItemEntity.setPrice(new BigDecimal(2));
        orderItemEntity.setMilkType(MilkType.NONE);

        List<OrderItemEntity> items = new ArrayList<>();
        items.add(orderItemEntity);


        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(OrderStatus.PENDING);
        orderEntity.setOrderNumber(1);
        orderEntity.setTotalPrice(new BigDecimal(10));
        orderEntity.setItems(items);

        OrderDto orderDto = orderMapper.toDto(orderEntity);
        assertEquals(1, orderDto.getItems().size());
    }

    @Test
    void orderEntity_to_orderDto_with_ignore_orderItem_test() {

        OrderItemDto dto = new OrderItemDto();
        dto.setCoffeeSize(CoffeeSize.LARGE);
        dto.setCount(2);
        dto.setCoffeeType(CoffeeType.ESPRESSO);
        dto.setPrice(new BigDecimal(2));
        dto.setMilkType(MilkType.NONE);

        List<OrderItemDto> items = new ArrayList<>();
        items.add(dto);

        OrderDto orderDto = new OrderDto();
        orderDto.setServeType(ServeType.ON_SITE);
        orderDto.setItems(items);

        OrderEntity orderEntity = orderMapper.toEntity(orderDto);
        assertTrue(CollectionUtils.isEmpty(orderEntity.getItems()));
        assertEquals(orderEntity.getServeType(), ServeType.ON_SITE);

    }
}
