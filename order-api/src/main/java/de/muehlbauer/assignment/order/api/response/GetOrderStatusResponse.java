package de.muehlbauer.assignment.order.api.response;

import de.muehlbauer.assignment.order.api.dto.OrderDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GetOrderStatusResponse implements Serializable {

    private List<OrderDto> orders ;
}
