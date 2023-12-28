package com.kodilla.currency_exchange_beckend.Repository;

import com.kodilla.currency_exchange_beckend.domain.Currencies;
import com.kodilla.currency_exchange_beckend.domain.Customers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersRepository extends CrudRepository<Customers,Long> {
    List<Customers> findAll();

    Customers findById(long id);

    Customers save(Currencies currency);

    Customers saveAndFlush(Currencies currency);

    void deleteById(long id);

    boolean existsById(long id);
}
