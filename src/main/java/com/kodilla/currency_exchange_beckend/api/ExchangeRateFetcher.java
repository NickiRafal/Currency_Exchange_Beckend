package com.kodilla.currency_exchange_beckend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ExchangeRateFetcher {

    public static void fetchAndDisplayData() {
        LocalDate currentDate = LocalDate.now().minusDays(1);
        LocalDate startDate = currentDate.minusDays(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = currentDate.format(formatter);

        try {
            URL url = new URL("http://api.nbp.pl/api/exchangerates/tables/C/" + formattedStartDate + "/" + formattedEndDate + "/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                ObjectMapper objectMapper = new ObjectMapper();
                ExchangeRate[] exchangeRates = objectMapper.readValue(response.toString(), ExchangeRate[].class);

                for (ExchangeRate exchangeRate : exchangeRates) {
                    System.out.println("Table: " + exchangeRate.getTable());
                    System.out.println("No: " + exchangeRate.getNo());
                    System.out.println("Trading Date: " + exchangeRate.getTradingDate());
                    System.out.println("Effective Date: " + exchangeRate.getEffectiveDate());
                    System.out.println("Rates: ");

                    for (Rate rate : exchangeRate.getRates()) {
                        if (rate.getCode().equals("USD") || rate.getCode().equals("EUR")) {
                            System.out.println("\tCurrency: " + rate.getCurrency());
                            System.out.println("\tCode: " + rate.getCode());
                            System.out.println("\tBid: " + rate.getBid());
                            System.out.println("\tAsk: " + rate.getAsk());
                        }
                    }
                    System.out.println("--------------------");
                }
            } else {
                System.out.println("Wystąpił problem podczas pobierania danych. Kod odpowiedzi: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
