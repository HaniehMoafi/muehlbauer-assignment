package de.muehlbauer.assignment.order.api.v1;

import de.muehlbauer.assignment.order.api.request.AddOrderRequest;
import de.muehlbauer.assignment.order.api.response.AddOrderResponse;
import de.muehlbauer.assignment.order.api.response.GeneralOrderApiResponse;
import de.muehlbauer.assignment.order.api.response.GetOrderStatusResponse;
import de.muehlbauer.assignment.order.exception.OrderServiceException;
import de.muehlbauer.assignment.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("v1/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<AddOrderResponse> addOrder(@Valid @RequestBody AddOrderRequest request) throws OrderServiceException {
        return ResponseEntity.ok().body(orderService.addOrder(request));

    }

    @PatchMapping("/cancel")
    public ResponseEntity<GeneralOrderApiResponse> cancelOrder(@RequestParam Integer orderNumber) throws OrderServiceException {

        orderService.cancelOrder(orderNumber);
        return new ResponseEntity<>(new GeneralOrderApiResponse(ACCEPTED.value(), "order was canceled successfully"), ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<GetOrderStatusResponse> getOrdersStatus() throws OrderServiceException {
        GetOrderStatusResponse response = new GetOrderStatusResponse();
        response.setOrders(orderService.getOrdersStatus());
        return ResponseEntity.ok().body(response);

    }

    @PatchMapping("/finish")
    public ResponseEntity<GeneralOrderApiResponse> finishOrder(@RequestParam Integer orderNumber) {

        orderService.finishOrder(orderNumber);
        return new ResponseEntity<>(new GeneralOrderApiResponse(ACCEPTED.value(), "order was canceled successfully"), ACCEPTED);
    }
}
