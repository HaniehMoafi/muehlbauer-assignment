package de.muehlbauer.assignment.order.repository;

import de.muehlbauer.assignment.order.api.enumeration.OrderStatus;
import de.muehlbauer.assignment.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    Optional<OrderEntity> findByIdAndStatus(Integer id, OrderStatus orderStatus);

    List<OrderEntity> findAllByStatus(OrderStatus status);

    Optional<OrderEntity> findByOrderNumber(Integer orderNumber);
}
