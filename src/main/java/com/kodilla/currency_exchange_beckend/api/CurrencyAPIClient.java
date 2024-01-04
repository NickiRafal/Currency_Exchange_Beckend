package com.kodilla.currency_exchange_beckend.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CurrencyAPIClient {

    public static String fetchDataFromAPI() {
        StringBuilder response = new StringBuilder();
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "http://api.nbp.pl/api/exchangerates/tables/C/";
        ResponseEntity<String> apiResponse = restTemplate.getForEntity(apiUrl, String.class);

        if (apiResponse.getStatusCode().is2xxSuccessful()) {
            response.append(apiResponse.getBody());
        } else {
            System.out.println("Wystąpił problem podczas pobierania danych. Kod odpowiedzi: " + apiResponse.getStatusCodeValue());
        }

        return response.toString();
    }
}
