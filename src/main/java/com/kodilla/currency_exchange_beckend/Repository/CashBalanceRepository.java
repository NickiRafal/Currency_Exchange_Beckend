package com.kodilla.currency_exchange_beckend.Repository;

import com.kodilla.currency_exchange_beckend.domain.CashBalance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CashBalanceRepository extends CrudRepository<CashBalance, String> {
    List<CashBalance> findAll();

    Optional<CashBalance> findByCurrencyCode(String currencyCode);


    CashBalance save(CashBalance cashBalance);


    void deleteByCurrencyCode(String currencyCode);


}
