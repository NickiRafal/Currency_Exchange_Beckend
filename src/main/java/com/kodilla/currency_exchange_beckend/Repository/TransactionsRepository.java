package com.kodilla.currency_exchange_beckend.Repository;


import com.kodilla.currency_exchange_beckend.domain.Transactions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionsRepository extends CrudRepository <Transactions,Long> {
    List<Transactions> findAll();
    Transactions findById(long id);

    Transactions save(Transactions transactions);

    Transactions saveAndFlush(Transactions transactions);

    void deleteById(long id);

    boolean existsById(long id);
}
