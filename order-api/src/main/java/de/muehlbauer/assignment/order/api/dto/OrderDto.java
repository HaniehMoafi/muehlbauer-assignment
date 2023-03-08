package de.muehlbauer.assignment.order.api.dto;

import de.muehlbauer.assignment.order.api.enumeration.ServeType;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderDto {

    @Valid
    @NotNull
    private List<OrderItemDto> items;
    private String baristaStatus;
    private Integer orderNumber;
    private ServeType serveType = ServeType.ON_SITE;

}
