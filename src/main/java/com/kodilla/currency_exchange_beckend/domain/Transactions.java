package com.kodilla.currency_exchange_beckend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
    @Id
    @GeneratedValue
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customers customer;

    private LocalDateTime transactionDateTime;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private BigDecimal exchangeRate;
    private BigDecimal fees;
    private String status;

    @ManyToOne
    @JoinColumn(name = "currencyFromId")
    private Currencies currencyFrom;

    @ManyToOne
    @JoinColumn(name = "currencyToId")
    private Currencies currencyTo;
}
