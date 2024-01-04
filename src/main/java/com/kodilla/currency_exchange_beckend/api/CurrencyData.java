package com.kodilla.currency_exchange_beckend.api;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CurrencyData {
    private String table;
    private String no;
    private String tradingDate;
    private String effectiveDate;
    private List<Rate> rates;

    public CurrencyData(String table, String no, String tradingDate, String effectiveDate, List<Rate> rates) {
        this.table = table;
        this.no = no;
        this.tradingDate = tradingDate;
        this.effectiveDate = effectiveDate;
        this.rates = rates;
    }


}
