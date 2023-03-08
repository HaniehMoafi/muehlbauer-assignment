package de.muehlbauer.assignment.order.entity;

import de.muehlbauer.assignment.order.api.enumeration.CoffeeSize;
import de.muehlbauer.assignment.order.api.enumeration.CoffeeType;
import de.muehlbauer.assignment.order.api.enumeration.MilkType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "ORDER_ITEM")
@Data
@Entity
public class OrderItemEntity extends BaseEntity {

    @Column(name = "COFFEE_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CoffeeType coffeeType;

    @Column(name = "MILK_TYPE")
    @Enumerated(EnumType.STRING)
    private MilkType milkType;

    @Column(name = "COFFEE_SIZE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CoffeeSize coffeeSize;

    @Column(name = "UNIT_PRICE")
    private BigDecimal price;

    @Column(name = "COUNT")
    private int count;

    @ManyToOne(targetEntity = OrderEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private OrderEntity order;


}
