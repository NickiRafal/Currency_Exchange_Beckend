package com.kodilla.currency_exchange_beckend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CashBalanceDTO {

    private String currencyCode;
    private BigDecimal balance;
    private LocalDateTime lastUpdated;
    private BigDecimal totalBalance;
    private Long currencyId;


}

