package net.mahdi.creditsimulatorservice.controller;

import net.mahdi.creditsimulatorservice.service.CurrencyConverterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class CurrencyConverterController {

    private final CurrencyConverterService currencyConverterService;

    public CurrencyConverterController(CurrencyConverterService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    @GetMapping("/")
    public String showConverterPage(Model model) {
        // Liste des devises supportées
        List<String> currencies = Arrays.asList(
                "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG",
                "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND",
                "BOB", "BRL", "BSD", "BTN", "BWP", "BYN", "BZD", "CAD", "CDF",
                "CHF", "CLP", "CNY", "COP", "CRC", "CUP", "CVE", "CZK", "DJF",
                "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP",
                "FOK", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ",
                "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP",
                "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES",
                "KGS", "KHR", "KID", "KMF", "KRW", "KWD", "KYD", "KZT", "LAK",
                "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL", "MGA", "MKD",
                "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR",
                "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB",
                "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD",
                "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP",
                "SLE", "SLL", "SOS", "SRD", "SSP", "STN", "SYP", "SZL", "THB",
                "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TVD", "TWD", "TZS",
                "UAH", "UGX", "USD", "UYU", "UZS", "VES", "VND", "VUV", "WST",
                "XAF", "XCD", "XOF", "XPF", "YER", "ZAR", "ZMW", "ZWL"
        );

        model.addAttribute("currencies", currencies);
        return "converter";
    }

    @PostMapping("/convert")
    public String convertCurrency(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("amount") double amount,
            Model model) {
        try {
            double result = currencyConverterService.convertCurrency(from, to, amount);
            model.addAttribute("result", result);
            model.addAttribute("from", from);
            model.addAttribute("to", to);
            model.addAttribute("amount", amount);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        // Renvoyer la liste des devises pour l'affichage
        List<String> currencies = Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "INR", "CNY", "CHF");
        model.addAttribute("currencies", currencies);
        return "converter";
    }
}
