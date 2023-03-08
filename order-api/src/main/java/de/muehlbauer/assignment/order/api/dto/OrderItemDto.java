package de.muehlbauer.assignment.order.api.dto;

import de.muehlbauer.assignment.order.api.enumeration.CoffeeSize;
import de.muehlbauer.assignment.order.api.enumeration.CoffeeType;
import de.muehlbauer.assignment.order.api.enumeration.MilkType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderItemDto {

    @NotNull
    private CoffeeType coffeeType;
    private MilkType milkType = MilkType.NONE;
    @NotNull
    private CoffeeSize coffeeSize;
    private int count = 1;
    @NotNull
    private BigDecimal price;



}
