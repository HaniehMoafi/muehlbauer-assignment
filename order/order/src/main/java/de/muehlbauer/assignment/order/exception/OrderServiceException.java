package de.muehlbauer.assignment.order.exception;


import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class OrderServiceException extends Exception {

    private String message;

    public OrderServiceException(String message) {
        super(message);
        this.message = message;
    }
}
