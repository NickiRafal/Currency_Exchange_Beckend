package com.kodilla.currency_exchange_beckend.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rate {
    private String currency;
    private String code;
    private double bid;
    private double ask;

    public Rate(String currency, String code, double bid, double ask) {
        this.currency = currency;
        this.code = code;
        this.bid = bid;
        this.ask = ask;
    }
    public Rate() {
    }

    @Override
    public String toString() {
        return "Currency: " + currency + ", Code: " + code + ", Bid: " + bid + ", Ask: " + ask;
    }
}
