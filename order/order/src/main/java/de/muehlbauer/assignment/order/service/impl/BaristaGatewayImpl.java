package de.muehlbauer.assignment.order.service.impl;


import de.muehlbauer.assignment.barista.api.request.AddOrderRequest;
import de.muehlbauer.assignment.barista.api.response.GeneralBaristaApiResponse;
import de.muehlbauer.assignment.barista.api.response.GetOrderStatus;
import de.muehlbauer.assignment.order.exception.OrderServiceException;
import de.muehlbauer.assignment.order.service.BaristaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static de.muehlbauer.assignment.order.util.Constant.BARISTA_BASE_URL;

@Service
@RequiredArgsConstructor
public class BaristaGatewayImpl implements BaristaGateway {

    private final RestTemplate restTemplate;


    @Override
    public void addBaristaOrder(Integer orderNumber) throws OrderServiceException {
        AddOrderRequest orderRequest = new AddOrderRequest();

        orderRequest.setOrderNumber(orderNumber);
        HttpEntity<AddOrderRequest> request = new HttpEntity<>(orderRequest);
        GeneralBaristaApiResponse response = null;
        try {
            response = restTemplate.postForEntity(BARISTA_BASE_URL, request, GeneralBaristaApiResponse.class).getBody();
            checkBaristaHttpStatus(response);
        } catch (Throwable t) {
            handleBaristaException(t, "order.barista.add.not.completed");
        }
    }

    @Override
    public void cancelBaristaOrder(Integer orderNumber) throws OrderServiceException {
        String url = urlBuilder(BARISTA_BASE_URL, "orderNumber", orderNumber);
        try {
            GeneralBaristaApiResponse response = restTemplate.exchange(url, HttpMethod.DELETE, null, GeneralBaristaApiResponse.class).getBody();
            checkBaristaHttpStatus(response);
        } catch (Throwable t) {
            handleBaristaException(t, "order.barista.cancel.order.not.completed");
        }
    }

    @Override
    public Map<Integer, String> getBaristaOrderStatus(List<Integer> orderNumbers) throws OrderServiceException {
        String url = urlBuilder(BARISTA_BASE_URL, "orderNumbers", orderNumbers.stream().map(String::valueOf).collect(Collectors.joining(",")));

        GetOrderStatus response = null;
        try {
            response = restTemplate.getForObject(url, GetOrderStatus.class);
            if (Objects.nonNull(response) && Objects.nonNull(response.getOrderStatus()))
                return response.getOrderStatus();
            else throw new OrderServiceException("order.barista.get.order.status.not.found");
        } catch (Throwable t) {
            handleBaristaException(t ,"order.barista.get.order.status.has.problem");
        }
        return null;
    }

    private <T> String urlBuilder(String url, String paramName, T paramValue) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam(paramName, paramValue);
        return builder.toUriString();
    }

    private void checkBaristaHttpStatus(GeneralBaristaApiResponse response) throws OrderServiceException {
        HttpStatus status = HttpStatus.valueOf(response.getStatusCode());
        if (!status.is2xxSuccessful()) throw new OrderServiceException(response.getMessage());
    }

    private void handleBaristaException(Throwable t, String defaultException) throws OrderServiceException {
        if (t instanceof OrderServiceException) throw (OrderServiceException) t;
        else throw new OrderServiceException(defaultException);
    }


}
