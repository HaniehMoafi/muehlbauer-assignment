package de.muehlbauer.assignment.order.service;

import de.muehlbauer.assignment.order.api.dto.OrderDto;
import de.muehlbauer.assignment.order.api.request.AddOrderRequest;
import de.muehlbauer.assignment.order.api.response.AddOrderResponse;
import de.muehlbauer.assignment.order.exception.OrderServiceException;

import java.util.List;

public interface OrderService {

    AddOrderResponse addOrder(AddOrderRequest request) throws OrderServiceException;

    void cancelOrder(Integer orderNumber) throws OrderServiceException;

    List<OrderDto> getOrdersStatus() throws OrderServiceException;

    void finishOrder(Integer orderNumber);
}
