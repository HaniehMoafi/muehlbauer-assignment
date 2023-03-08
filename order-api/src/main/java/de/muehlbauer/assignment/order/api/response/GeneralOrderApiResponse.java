package de.muehlbauer.assignment.order.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralOrderApiResponse {

    private int statusCode;

    private String message;
}
