package de.muehlbauer.assignment.order.service;


import de.muehlbauer.assignment.order.api.enumeration.*;
import de.muehlbauer.assignment.order.entity.OrderEntity;
import de.muehlbauer.assignment.order.entity.OrderItemEntity;
import de.muehlbauer.assignment.order.repository.OrderItemRepository;
import de.muehlbauer.assignment.order.repository.OrderRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    @BeforeAll
    void setUp() {
        orderItemRepository.save(creatOrderItem());
    }

    @AfterAll
    void clear() {
        orderItemRepository.deleteAll();
    }


    @Test
    void order_can_not_be_finished() {

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> orderService.finishOrder(100),
                "Expected can not finished the order because the order was not found"
        );
        assertTrue(thrown.getMessage().contentEquals("order.not.found"));

    }

    @Test
    void order_can_be_finished() {
        orderService.finishOrder(1);
        Optional<OrderEntity> byOrderNumber = orderRepository.findByOrderNumber(1);
        assertEquals(byOrderNumber.get().getStatus(), OrderStatus.FINISHED);

    }

    private OrderItemEntity creatOrderItem() {
        OrderEntity entity = new OrderEntity();
        entity.setOrderNumber(1);
        entity.setStatus(OrderStatus.PENDING);
        entity.setServeType(ServeType.TAKEAWAY);
        entity.setTotalPrice(new BigDecimal(10));


        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setCoffeeSize(CoffeeSize.MEDIUM);
        orderItem.setCount(1);
        orderItem.setCoffeeType(CoffeeType.CAPPUCCINO);
        orderItem.setMilkType(MilkType.NONE);
        orderItem.setPrice(new BigDecimal(10));
        orderItem.setOrder(entity);

        return orderItem;
    }

}
