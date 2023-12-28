package com.kodilla.currency_exchange_beckend.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CurrenciesDTO {

    private Long currencyId;
    private String currencyCode;
    private String currencyName;
    private BigDecimal buyingRate;
    private BigDecimal sellingRate;
    private BigDecimal fees;


}
