package de.muehlbauer.assignment.order.exception.handler;


import de.muehlbauer.assignment.order.api.response.GeneralOrderApiResponse;
import de.muehlbauer.assignment.order.exception.OrderServiceException;
import de.muehlbauer.assignment.order.util.MessageBundler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralOrderApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        GeneralOrderApiResponse response = new GeneralOrderApiResponse();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            response.setMessage(fieldName + " : " + errorMessage);
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderServiceException.class)
    public ResponseEntity<GeneralOrderApiResponse> handleValidationExceptions(OrderServiceException ex) {
        GeneralOrderApiResponse response = new GeneralOrderApiResponse();
        String expMessage = MessageBundler.getExpMessage(ex.getMessage());
        response.setMessage(expMessage);
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GeneralOrderApiResponse> handleValidationExceptions(RuntimeException ex) {
        GeneralOrderApiResponse response = new GeneralOrderApiResponse();
        String expMessage = MessageBundler.getExpMessage(ex.getMessage());
        response.setMessage(expMessage);
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
