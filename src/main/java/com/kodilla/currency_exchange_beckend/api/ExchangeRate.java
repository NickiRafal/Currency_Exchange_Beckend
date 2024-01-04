package com.kodilla.currency_exchange_beckend.api;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExchangeRate {
    private String table;
    private String no;
    private String tradingDate;
    private String effectiveDate;
    private List<Rate> rates;


    }

