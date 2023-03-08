package de.muehlbauer.assignment.order.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Order Service API", version = "1.0", description = "api document for order service"))
public class SwaggerConfiguration {
}
