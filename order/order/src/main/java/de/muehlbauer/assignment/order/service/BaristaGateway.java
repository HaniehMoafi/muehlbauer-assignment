package de.muehlbauer.assignment.order.service;

import de.muehlbauer.assignment.order.exception.OrderServiceException;

import java.util.List;
import java.util.Map;

public interface BaristaGateway {

    void addBaristaOrder(Integer orderNumber) throws OrderServiceException;

    void cancelBaristaOrder(Integer orderNumber) throws OrderServiceException;

    Map<Integer, String> getBaristaOrderStatus(List<Integer> orderNumbers) throws OrderServiceException;
}
