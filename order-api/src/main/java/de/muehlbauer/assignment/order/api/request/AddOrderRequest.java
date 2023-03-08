package de.muehlbauer.assignment.order.api.request;

import de.muehlbauer.assignment.order.api.dto.OrderDto;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddOrderRequest implements Serializable {

    @Valid
    @NotNull
    private OrderDto order;


}
