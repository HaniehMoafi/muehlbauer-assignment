package de.muehlbauer.assignment.order.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDto {

    private String itemName;
    private BigDecimal price;
    private int count;
}
