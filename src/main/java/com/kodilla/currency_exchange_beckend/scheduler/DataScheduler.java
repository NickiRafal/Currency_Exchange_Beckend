package com.kodilla.currency_exchange_beckend.scheduler;

import com.kodilla.currency_exchange_beckend.api.CurrencyAPIClient;
import com.kodilla.currency_exchange_beckend.api.ExchangeRateFetcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataScheduler {

    @Scheduled(cron = "0 * 8,12,18 * * *")
    public void fetchDataAndDisplay() {
        ExchangeRateFetcher.fetchAndDisplayData();
        CurrencyAPIClient.fetchDataFromAPI();
    }
}