package de.muehlbauer.assignment.order.entity;

import de.muehlbauer.assignment.order.api.enumeration.OrderStatus;
import de.muehlbauer.assignment.order.api.enumeration.ServeType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Data
@Table(name = "ORDER_TABLE")
@Entity
public class OrderEntity extends BaseEntity {

    @Column(name = "ORDER_NUMBER", nullable = false)
    private Integer orderNumber;

    @Column(name = "SERVE_TYPE")
    @Enumerated(EnumType.STRING)
    private ServeType serveType ;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID")
    private List<OrderItemEntity> items;

}
