package net.mahdi.creditsimulatorservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class CurrencyConverterService {

    private final RestTemplate restTemplate;

    public CurrencyConverterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double convertCurrency(String from, String to, double amount) {
        String apiUrl = String.format("""
                https://api.exchangerate-api.com/v4/latest/%s""", from);
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

        if (response != null && response.containsKey("rates")) {
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            Double rate = rates.get(to);
            if (rate != null) {
                return amount * rate;
            }
        }
        throw new IllegalArgumentException("Invalid currency code or rate not available.");
    }
}
