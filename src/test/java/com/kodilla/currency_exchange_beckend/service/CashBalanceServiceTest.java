package com.kodilla.currency_exchange_beckend.service;

import com.kodilla.currency_exchange_beckend.Repository.CashBalanceRepository;
import com.kodilla.currency_exchange_beckend.domain.CashBalance;
import com.kodilla.currency_exchange_beckend.domain.CashBalanceDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CashBalanceServiceTest {

    @Autowired
    private CashBalanceRepository cashBalanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CashBalanceService cashBalanceService;

    @Test
    @DirtiesContext
    @Transactional
    public void testGetAllCashBalances_WhenCashBalancesExist() {
        // Given
        CashBalance cashBalance1 = new CashBalance();
        cashBalance1.setCurrencyCode("EUR");
        cashBalance1.setBalance(new BigDecimal("100.00"));

        CashBalance cashBalance2 = new CashBalance();
        cashBalance2.setCurrencyCode("USD");
        cashBalance2.setBalance(new BigDecimal("200.00"));

        cashBalanceRepository.saveAll(Arrays.asList(cashBalance1, cashBalance2));

        // When
        List<CashBalanceDTO> result = cashBalanceService.getAllCashBalances();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    @DirtiesContext
    @Transactional
    public void testGetAllCashBalances_WhenNoCashBalancesExist() {

        // When
        List<CashBalanceDTO> result = cashBalanceService.getAllCashBalances();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    @DirtiesContext
    @Transactional
    public void testGetCashBalanceByCurrencyCode_WhenCashBalanceExists() {
        // Given
        CashBalance cashBalance = new CashBalance();
        cashBalance.setCurrencyCode("USD");
        cashBalance.setBalance(new BigDecimal("100.00"));

        cashBalanceRepository.save(cashBalance);

        // When
        Optional<CashBalanceDTO> result = cashBalanceService.getCashBalanceByCurrencyCode("USD");

        // Then
        assertTrue(result.isPresent());
        assertEquals(cashBalance.getCurrencyCode(), result.get().getCurrencyCode());
        assertEquals(cashBalance.getBalance(), result.get().getBalance());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void testGetCashBalanceByCurrencyCode_WhenNoCashBalanceExists() {
        // Given

        // When
        Optional<CashBalanceDTO> result = cashBalanceService.getCashBalanceByCurrencyCode("EUR");

        // Then
        assertTrue(result.isEmpty());
    }
    @Test
    @DirtiesContext
    @Transactional
    public void testAddOrUpdateCashBalance_WhenAdd() {
        // Given
        CashBalanceDTO cashBalanceDTO = new CashBalanceDTO();
        cashBalanceDTO.setCurrencyCode("USD");
        cashBalanceDTO.setBalance(new BigDecimal("100.00"));

        // When
        CashBalanceDTO result = cashBalanceService.addOrUpdateCashBalance(cashBalanceDTO);

        // Then
        assertNotNull(result);
        assertEquals(cashBalanceDTO.getCurrencyCode(), result.getCurrencyCode());
        assertEquals(cashBalanceDTO.getBalance(), result.getBalance());

    }

    @Test
    @DirtiesContext
    @Transactional
    public void testAddOrUpdateCashBalance_WhenUpdate() {
        // Given
        CashBalance cashBalance = new CashBalance();
        cashBalance.setCurrencyCode("USD");
        cashBalance.setBalance(new BigDecimal("100.00"));
        cashBalanceRepository.save(cashBalance);

        CashBalanceDTO updatedCashBalanceDTO = new CashBalanceDTO();
        updatedCashBalanceDTO.setCurrencyCode("USD");
        updatedCashBalanceDTO.setBalance(new BigDecimal("200.00"));

        // When
        CashBalanceDTO result = cashBalanceService.addOrUpdateCashBalance(updatedCashBalanceDTO);

        // Then
        assertNotNull(result);
        assertEquals(updatedCashBalanceDTO.getCurrencyCode(), result.getCurrencyCode());
        assertEquals(updatedCashBalanceDTO.getBalance(), result.getBalance());

    }
    @Test
    @DirtiesContext
    @Transactional
    public void testDeleteCashBalanceByCurrencyCode_WhenCashBalanceExists() {
        // Given
        CashBalance cashBalance = new CashBalance();
        cashBalance.setCurrencyCode("USD");
        cashBalance.setBalance(new BigDecimal("100.00"));

        cashBalanceRepository.save(cashBalance);

        // When
        boolean result = cashBalanceService.deleteCashBalanceByCurrencyCode("USD");

        // Then
        assertTrue(result);
        Optional<CashBalance> deletedCashBalance = cashBalanceRepository.findByCurrencyCode("USD");
        assertTrue(deletedCashBalance.isEmpty());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void testDeleteCashBalanceByCurrencyCode_WhenNoCashBalanceExists() {
        // Given

        // When
        boolean result = cashBalanceService.deleteCashBalanceByCurrencyCode("EUR");

        // Then
        assertFalse(result);
    }
}
