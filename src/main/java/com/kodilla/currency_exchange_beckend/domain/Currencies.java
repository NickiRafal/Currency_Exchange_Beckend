package com.kodilla.currency_exchange_beckend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Currencies {
    @Id
    @GeneratedValue
    private Long currencyId;

    private String currencyCode;
    private String currencyName;
    private BigDecimal buyingRate;
    private BigDecimal sellingRate;
    private BigDecimal fees;

    @OneToMany(mappedBy = "currencyFrom")
    private List<Transactions> transactionsFrom;

    @OneToMany(mappedBy = "currencyTo")
    private List<Transactions> transactionsTo;

    public Currencies(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Currencies(Long currencyId, String currencyCode) {
        this.currencyId = currencyId;
        this.currencyCode = currencyCode;
    }
}

