package com.kodilla.currency_exchange_beckend.service;

import com.kodilla.currency_exchange_beckend.Repository.CurrenciesRepository;
import com.kodilla.currency_exchange_beckend.Repository.CustomersRepository;
import com.kodilla.currency_exchange_beckend.Repository.TransactionsRepository;
import com.kodilla.currency_exchange_beckend.domain.Customers;
import com.kodilla.currency_exchange_beckend.domain.Currencies;
import com.kodilla.currency_exchange_beckend.domain.Transactions;
import com.kodilla.currency_exchange_beckend.domain.TransactionsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionsServiceTest {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private CurrenciesRepository currenciesRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private TransactionsService transactionsService;

    @Test
    public void testGetAllTransactions_WhenTransactionsExist() {
        // Given
        Customers customer = new Customers();
        customer.setCustomerId(1L);
        customer.setFirstName("Rafal");

        Currencies currencyFrom = new Currencies();
        currencyFrom.setCurrencyCode("USD");
        currencyFrom.setCurrencyName("US Dollar");
        currencyFrom.setBuyingRate(BigDecimal.valueOf(1.0));
        currencyFrom.setSellingRate(BigDecimal.valueOf(1.0));
        currencyFrom.setFees(BigDecimal.valueOf(0.5));

        currenciesRepository.save(currencyFrom);

        Currencies currencyTo = new Currencies();
        currencyTo.setCurrencyCode("PL");
        currencyTo.setCurrencyName("Polski zloty");
        currencyTo.setBuyingRate(BigDecimal.valueOf(1.0));
        currencyTo.setSellingRate(BigDecimal.valueOf(1.0));
        currencyTo.setFees(BigDecimal.valueOf(0.5));
        currenciesRepository.save(currencyTo);

        Transactions transaction1 = new Transactions();
        transaction1.setCustomer(customer);
        transaction1.setTransactionDateTime(LocalDateTime.now());
        transaction1.setAmountFrom(BigDecimal.valueOf(100));
        transaction1.setAmountTo(BigDecimal.valueOf(90));
        transaction1.setExchangeRate(BigDecimal.valueOf(0.9));
        transaction1.setFees(BigDecimal.valueOf(10));
        transaction1.setStatus("Completed");
        transaction1.setCurrencyFrom(currencyFrom);
        transaction1.setCurrencyTo(currencyTo);

        Transactions transaction2 = new Transactions();
        transaction2.setCustomer(customer);
        transaction2.setTransactionDateTime(LocalDateTime.now());
        transaction2.setAmountFrom(BigDecimal.valueOf(200));
        transaction2.setAmountTo(BigDecimal.valueOf(180));
        transaction2.setExchangeRate(BigDecimal.valueOf(0.9));
        transaction2.setFees(BigDecimal.valueOf(20));
        transaction2.setStatus("Completed");
        transaction2.setCurrencyFrom(currencyFrom);
        transaction2.setCurrencyTo(currencyTo);

        customersRepository.save(customer);
        transactionsRepository.save(transaction1);
        transactionsRepository.save(transaction2);


        // When
        List<TransactionsDTO> result = transactionsService.getAllTransactions();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    @DirtiesContext
    @Transactional
    public void testGetAllTransactions_WhenNoTransactionsExist() {
        // Given that there are no transactions in the repository

        // When
        List<TransactionsDTO> result = transactionsService.getAllTransactions();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    @DirtiesContext
    @Transactional
    public void testGetTransactionById_WhenTransactionExists() {
        // Given
        Transactions transaction = new Transactions();
        transaction.setTransactionId(1L);
        transaction.setTransactionDateTime(LocalDateTime.now());
        transaction.setAmountFrom(BigDecimal.valueOf(100));
        transaction.setAmountTo(BigDecimal.valueOf(90));
        transaction.setExchangeRate(BigDecimal.valueOf(0.9));
        transaction.setFees(BigDecimal.valueOf(10));
        transaction.setStatus("Completed");

        transactionsRepository.save(transaction);

        // When
        TransactionsDTO result = transactionsService.getTransactionById(1L);

        // Then
        assertNotNull(result);
        assertEquals(transaction.getTransactionId(), result.getTransactionId());

    }

    @Test
    @DirtiesContext
    @Transactional
    public void testGetTransactionById_WhenTransactionDoesNotExist() {
        // Given - no transactions added to repository

        // When
        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> transactionsService.getTransactionById(1L)
        );

        // Then
        assertEquals("Transaction not found", exception.getMessage());
    }
    @Test
    @DirtiesContext
    @Transactional
    public void testAddTransaction() {
        // Given
        TransactionsDTO transactionDTO = new TransactionsDTO();
        transactionDTO.setTransactionId(1L);
        transactionDTO.setTransactionDateTime(LocalDateTime.now());
        transactionDTO.setAmountFrom(BigDecimal.valueOf(100));
        transactionDTO.setAmountTo(BigDecimal.valueOf(90));
        transactionDTO.setExchangeRate(BigDecimal.valueOf(0.9));
        transactionDTO.setFees(BigDecimal.valueOf(10));
        transactionDTO.setStatus("Completed");

        // When
        TransactionsDTO result = transactionsService.addTransaction(transactionDTO);

        // Then
        assertNotNull(result);
        assertEquals(transactionDTO.getTransactionId(), result.getTransactionId());

    }
    @Test
    @DirtiesContext
    @Transactional
    public void testUpdateTransaction() {
        // Given
        TransactionsDTO updatedTransactionDTO = new TransactionsDTO();
        updatedTransactionDTO.setTransactionDateTime(LocalDateTime.now());
        updatedTransactionDTO.setAmountFrom(BigDecimal.valueOf(200));
        updatedTransactionDTO.setAmountTo(BigDecimal.valueOf(180));
        updatedTransactionDTO.setExchangeRate(BigDecimal.valueOf(0.9));
        updatedTransactionDTO.setFees(BigDecimal.valueOf(20));
        updatedTransactionDTO.setStatus("Updated");

        Long id = 1L;

        Transactions existingTransaction = new Transactions();
        existingTransaction.setTransactionId(id);
        existingTransaction.setTransactionDateTime(LocalDateTime.of(2023, 1, 1, 12, 0));
        existingTransaction.setAmountFrom(BigDecimal.valueOf(100));
        existingTransaction.setAmountTo(BigDecimal.valueOf(90));
        existingTransaction.setExchangeRate(BigDecimal.valueOf(0.9));
        existingTransaction.setFees(BigDecimal.valueOf(10));
        existingTransaction.setStatus("Completed");

        transactionsRepository.save(existingTransaction);

        // When
        Optional<TransactionsDTO> result = transactionsService.updateTransaction(id, updatedTransactionDTO);

        // Then
        assertTrue(result.isPresent());
        TransactionsDTO updatedResult = result.get();
        assertNotNull(updatedResult);
        assertEquals(id, updatedResult.getTransactionId());
        assertEquals(updatedTransactionDTO.getTransactionDateTime(), updatedResult.getTransactionDateTime());
        assertEquals(updatedTransactionDTO.getAmountFrom(), updatedResult.getAmountFrom());
        assertEquals(updatedTransactionDTO.getAmountTo(), updatedResult.getAmountTo());
        assertEquals(updatedTransactionDTO.getExchangeRate(), updatedResult.getExchangeRate());
        assertEquals(updatedTransactionDTO.getFees(), updatedResult.getFees());
        assertEquals(updatedTransactionDTO.getStatus(), updatedResult.getStatus());
    }
    @Test
    public void testDeleteTransaction() {
        // Given
        Transactions existingTransaction = new Transactions();
        existingTransaction.setTransactionId(1L);
        existingTransaction.setTransactionDateTime(LocalDateTime.of(2023, 1, 1, 12, 0));
        existingTransaction.setAmountFrom(BigDecimal.valueOf(100));
        existingTransaction.setAmountTo(BigDecimal.valueOf(90));
        existingTransaction.setExchangeRate(BigDecimal.valueOf(0.9));
        existingTransaction.setFees(BigDecimal.valueOf(10));
        existingTransaction.setStatus("Completed");

        transactionsRepository.save(existingTransaction);

        // When
        boolean result = transactionsService.deleteTransaction(1L);

        // Then
        assertTrue(result);
        assertFalse(transactionsRepository.existsById(1L));
    }
}
