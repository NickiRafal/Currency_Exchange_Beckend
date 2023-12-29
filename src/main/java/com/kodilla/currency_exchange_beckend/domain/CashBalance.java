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
public class CashBalance {

    @Id
    private String currencyCode;
    private BigDecimal balance;
    private LocalDateTime lastUpdated;
    private BigDecimal totalBalance;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "currencyId")
    private Currencies currency;


}
