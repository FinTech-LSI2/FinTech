package net.mahdi.financeservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CurrencyConversionRequest {
    private String from;
    private String to;
    private double amount;

}
