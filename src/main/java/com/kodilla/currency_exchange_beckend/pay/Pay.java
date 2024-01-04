package com.kodilla.currency_exchange_beckend.pay;
public class Pay {
    private static final int INITIAL_FUNDS = 1000;
    private int availableFunds;

    public Pay() {
        this.availableFunds = INITIAL_FUNDS;
    }

    public double getAvailableFunds() {
        return availableFunds;
    }

    public boolean exchangeCurrency(int amountToExchange, String currencyCode) {
        // Tutaj umieść logikę wymiany waluty
        // Sprawdzanie dostępnych środków, kursów wymiany itp.
        // Zwróć true, jeśli wymiana zakończy się sukcesem, w przeciwnym razie false

        // Przykładowa logika
        if (amountToExchange <= availableFunds) {
            // Wymiana waluty...
            availableFunds -= amountToExchange;
            System.out.println(amountToExchange + " PLN exchanged to " + currencyCode);
            return true;
        } else {
            System.out.println("Insufficient funds");
            return false;
        }
    }
}
