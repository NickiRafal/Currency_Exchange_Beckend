package com.kodilla.currency_exchange_beckend.Repository;

import com.kodilla.currency_exchange_beckend.domain.Currencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrenciesRepository extends JpaRepository<Currencies, Long> {

    List<Currencies> findAll();

    Currencies findById(long id);

    Currencies save(Currencies currency);

    Currencies saveAndFlush(Currencies currency);

    void deleteById(long id);

    boolean existsById(long id);



}
