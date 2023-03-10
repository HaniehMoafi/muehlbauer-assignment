package de.muehlbauer.assignment.order.service.impl;

import de.muehlbauer.assignment.order.api.dto.OrderDto;
import de.muehlbauer.assignment.order.api.dto.OrderItemDto;
import de.muehlbauer.assignment.order.api.enumeration.OrderStatus;
import de.muehlbauer.assignment.order.api.request.AddOrderRequest;
import de.muehlbauer.assignment.order.api.response.AddOrderResponse;
import de.muehlbauer.assignment.order.entity.OrderEntity;
import de.muehlbauer.assignment.order.entity.OrderItemEntity;
import de.muehlbauer.assignment.order.exception.OrderServiceException;
import de.muehlbauer.assignment.order.mapper.OrderItemMapper;
import de.muehlbauer.assignment.order.mapper.OrderMapper;
import de.muehlbauer.assignment.order.repository.OrderItemRepository;
import de.muehlbauer.assignment.order.repository.OrderRepository;
import de.muehlbauer.assignment.order.service.BaristaGateway;
import de.muehlbauer.assignment.order.service.OrderService;
import de.muehlbauer.assignment.order.util.OrderNumberGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final BaristaGateway baristaGateway;


    @Override
    @Transactional(rollbackFor = {OrderServiceException.class})
    public AddOrderResponse addOrder(AddOrderRequest request) throws OrderServiceException {

        OrderEntity order = orderMapper.toEntity(request.getOrder());
        Integer orderNumber = OrderNumberGenerator.getValue();
        order.setOrderNumber(orderNumber);

        List<OrderItemEntity> items = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItemDto dto : request.getOrder().getItems()) {
            OrderItemEntity orderItemEntity = orderItemMapper.toEntity(dto);
            orderItemEntity.setOrder(order);
            items.add(orderItemEntity);
            totalPrice = totalPrice.add(orderItemEntity.getPrice());
            //todo count
        }
        order.setTotalPrice(totalPrice);
        orderItemRepository.saveAll(items);

        baristaGateway.addBaristaOrder(orderNumber);

        AddOrderResponse response = new AddOrderResponse();
        response.setOrderNumber(orderNumber);
        return response;
    }

    @Override
    @Transactional(rollbackFor = {OrderServiceException.class})
    public void cancelOrder(Integer orderNumber) throws OrderServiceException {
        Optional<OrderEntity> foundOrder = orderRepository.findByOrderNumber(orderNumber);
        foundOrder.ifPresentOrElse(o -> {
            o.setStatus(OrderStatus.CANCELED);
            orderRepository.save(o);
        }, () -> {
            throw new RuntimeException("order.not.found");
        });
        baristaGateway.cancelBaristaOrder(orderNumber);
    }

    @Override
    public List<OrderDto> getOrdersStatus() throws OrderServiceException {
        List<OrderEntity> pendingOrders = orderRepository.findAllByStatus(OrderStatus.PENDING);
        List<Integer> orderNumbers = new ArrayList<>();
        List<OrderDto> orderDTOList = new ArrayList<>();
        pendingOrders.forEach(o -> {
            orderNumbers.add(o.getOrderNumber());
            orderDTOList.add(orderMapper.toDto(o));
        });
        Map<Integer, String> baristaOrderStatus = baristaGateway.getBaristaOrderStatus(orderNumbers);
        orderDTOList.forEach(o -> o.setBaristaStatus(baristaOrderStatus.get(o.getOrderNumber())));
        return orderDTOList;
    }

    @Override
    public void finishOrder(Integer orderNumber) {
        Optional<OrderEntity> foundOrder = orderRepository.findByOrderNumber(orderNumber);
        foundOrder.ifPresentOrElse(orderEntity -> {
            orderEntity.setStatus(OrderStatus.FINISHED);
            orderRepository.save(orderEntity);
        }, () -> {
            throw new RuntimeException("order.not.found");
        });

    }

}
