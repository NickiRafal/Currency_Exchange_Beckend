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
public class TransactionsDTO {

    private Long transactionId;
    private Long customerId;
    private LocalDateTime transactionDateTime;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private BigDecimal exchangeRate;
    private BigDecimal fees;
    private String status;
    private Long currencyFromId;
    private Long currencyToId;



}
