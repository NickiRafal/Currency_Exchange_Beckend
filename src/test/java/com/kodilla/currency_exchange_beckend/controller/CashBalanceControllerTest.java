package com.kodilla.currency_exchange_beckend.controller;

import com.kodilla.currency_exchange_beckend.domain.CashBalance;
import com.kodilla.currency_exchange_beckend.domain.CashBalanceDTO;
import com.kodilla.currency_exchange_beckend.domain.Currencies;
import com.kodilla.currency_exchange_beckend.service.CashBalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CashBalanceControllerTest {

    private CashBalanceController cashBalanceController;
    private CashBalanceService cashBalanceService;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        cashBalanceService = mock(CashBalanceService.class);
        cashBalanceController = new CashBalanceController(cashBalanceService);
        modelMapper = new ModelMapper();
    }

    @Test
    public void getAllCashBalances_ShouldReturnAllCashBalances() {
        // Given
        Currencies currency = new Currencies();
        currency.setCurrencyName("US Dollar");

        CashBalance cashBalance1 = new CashBalance();
        cashBalance1.setCurrencyCode("USD");
        cashBalance1.setBalance(new BigDecimal("1000.00"));
        cashBalance1.setLastUpdated(LocalDateTime.now());
        cashBalance1.setTotalBalance(new BigDecimal("1500.00"));
        cashBalance1.setCurrency(currency);

        CashBalance cashBalance2 = new CashBalance();
        cashBalance2.setCurrencyCode("EUR");
        cashBalance2.setBalance(new BigDecimal("2000.00"));
        cashBalance2.setLastUpdated(LocalDateTime.now());
        cashBalance2.setTotalBalance(new BigDecimal("2500.00"));
        cashBalance2.setCurrency(currency);

        List<CashBalance> mockCashBalances = List.of(cashBalance1, cashBalance2);
        List<CashBalanceDTO> mockCashBalancesDTO = mockCashBalances.stream()
                .map(cashBalance -> modelMapper.map(cashBalance, CashBalanceDTO.class))
                .collect(Collectors.toList());

        when(cashBalanceService.getAllCashBalances()).thenReturn(mockCashBalancesDTO);

        // When
        List<CashBalanceDTO> response = cashBalanceController.getAllCashBalances().getBody();

        // Then
        assertEquals(mockCashBalances.size(), response.size());
        assertEquals(mockCashBalances.get(0).getCurrencyCode(), response.get(0).getCurrencyCode());
        assertEquals(mockCashBalances.get(1).getCurrencyCode(), response.get(1).getCurrencyCode());

    }
    @Test
    public void getCashBalanceByCurrencyCode_ShouldReturnCashBalance() {
        // Given
        String currencyCode = "USD";
        Currencies currency = new Currencies();
        currency.setCurrencyName("US Dollar");

        CashBalance cashBalance = new CashBalance();
        cashBalance.setCurrencyCode(currencyCode);
        cashBalance.setBalance(new BigDecimal("1000.00"));
        cashBalance.setLastUpdated(LocalDateTime.now());
        cashBalance.setTotalBalance(new BigDecimal("1500.00"));
        cashBalance.setCurrency(currency);

        CashBalanceDTO cashBalanceDTO = modelMapper.map(cashBalance, CashBalanceDTO.class);

        when(cashBalanceService.getCashBalanceByCurrencyCode(currencyCode)).thenReturn(Optional.of(cashBalanceDTO));

        // When
        ResponseEntity<CashBalanceDTO> response = cashBalanceController.getCashBalanceByCurrencyCode(currencyCode);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currencyCode, response.getBody().getCurrencyCode());
        assertEquals(cashBalance.getBalance(), response.getBody().getBalance());
        assertEquals(cashBalance.getLastUpdated(), response.getBody().getLastUpdated());
        assertEquals(cashBalance.getTotalBalance(), response.getBody().getTotalBalance());
        // Sprawdź inne pola, jeśli chcesz zweryfikować ich poprawność
    }
    @Test
    public void addOrUpdateCashBalance_ShouldReturnAddedOrUpdatedCashBalance() {
        // Given
        Currencies currency = new Currencies();
        currency.setCurrencyName("US Dollar");

        CashBalance cashBalance = new CashBalance();
        cashBalance.setCurrencyCode("USD");
        cashBalance.setBalance(new BigDecimal("1000.00"));
        cashBalance.setLastUpdated(LocalDateTime.now());
        cashBalance.setTotalBalance(new BigDecimal("1500.00"));
        cashBalance.setCurrency(currency);

        CashBalanceDTO cashBalanceDTO = modelMapper.map(cashBalance, CashBalanceDTO.class);

        when(cashBalanceService.addOrUpdateCashBalance(cashBalanceDTO)).thenReturn(cashBalanceDTO);

        // When
        ResponseEntity<CashBalanceDTO> response = cashBalanceController.addOrUpdateCashBalance(cashBalanceDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cashBalance.getCurrencyCode(), response.getBody().getCurrencyCode());
        assertEquals(cashBalance.getBalance(), response.getBody().getBalance());
        assertEquals(cashBalance.getLastUpdated(), response.getBody().getLastUpdated());
        assertEquals(cashBalance.getTotalBalance(), response.getBody().getTotalBalance());

    }
    @Test
    public void updateCashBalance_ShouldReturnUpdatedCashBalance() {
        // Given
        String currencyCode = "USD";
        Currencies currency = new Currencies();
        currency.setCurrencyName("US Dollar");

        CashBalance cashBalance = new CashBalance();
        cashBalance.setCurrencyCode(currencyCode);
        cashBalance.setBalance(new BigDecimal("1000.00"));
        cashBalance.setLastUpdated(LocalDateTime.now());
        cashBalance.setTotalBalance(new BigDecimal("1500.00"));
        cashBalance.setCurrency(currency);

        CashBalanceDTO cashBalanceDTO = modelMapper.map(cashBalance, CashBalanceDTO.class);

        when(cashBalanceService.updateCashBalance(currencyCode, cashBalanceDTO)).thenReturn(cashBalanceDTO);

        // When
        ResponseEntity<CashBalanceDTO> response = cashBalanceController.updateCashBalance(currencyCode, cashBalanceDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currencyCode, response.getBody().getCurrencyCode());
        assertEquals(cashBalance.getBalance(), response.getBody().getBalance());
        assertEquals(cashBalance.getLastUpdated(), response.getBody().getLastUpdated());
        assertEquals(cashBalance.getTotalBalance(), response.getBody().getTotalBalance());

    }
   
    @Test
    public void deleteCashBalanceByCurrencyCode_ShouldDeleteCashBalance() {
        // Given
        String currencyCode = "USD";
        Currencies currency = new Currencies();
        currency.setCurrencyName("US Dollar");

        CashBalance cashBalance = new CashBalance();
        cashBalance.setCurrencyCode(currencyCode);
        cashBalance.setBalance(new BigDecimal("1000.00"));
        cashBalance.setLastUpdated(LocalDateTime.now());
        cashBalance.setTotalBalance(new BigDecimal("1500.00"));
        cashBalance.setCurrency(currency);

        when(cashBalanceService.deleteCashBalanceByCurrencyCode(currencyCode)).thenReturn(true);

        // When
        ResponseEntity<Void> response = cashBalanceController.deleteCashBalanceByCurrencyCode(currencyCode);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

