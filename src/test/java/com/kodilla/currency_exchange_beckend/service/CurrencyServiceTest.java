package com.kodilla.currency_exchange_beckend.service;

import com.kodilla.currency_exchange_beckend.Repository.CurrenciesRepository;
import com.kodilla.currency_exchange_beckend.domain.Currencies;
import com.kodilla.currency_exchange_beckend.domain.CurrenciesDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class CurrencyServiceTest {

    @Autowired
    private CurrenciesRepository currenciesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CurrenciesService currenciesService;


    @Test
    @DirtiesContext
    @Transactional
    public void testUpdateCurrency() {
        // Given
        Currencies existingCurrency = new Currencies();
        existingCurrency.setCurrencyId(1L);
        existingCurrency.setCurrencyCode("EUR");

        currenciesRepository.save(existingCurrency);

        Long currencyId = 1L;

        CurrenciesDTO updatedDto = new CurrenciesDTO();
        updatedDto.setCurrencyId(currencyId);
        updatedDto.setCurrencyCode("USD");

        // When
        CurrenciesDTO result = currenciesService.updateCurrency(currencyId, updatedDto);

        // Then
        assertNotNull(result);
        assertEquals(updatedDto.getCurrencyCode(), result.getCurrencyCode());
        assertEquals(currencyId, result.getCurrencyId());
    }
    @Test
    @DirtiesContext
    @Transactional
    public void testDeleteCurrency() {
        //Given
        Currencies currency = new Currencies();
        currency.setCurrencyId(1L);
        currency.setCurrencyCode("USD");
        currenciesRepository.save(currency);
        assertTrue(currenciesRepository.existsById(1L));
        //When
        boolean result = currenciesService.deleteCurrency(1L);
        //Then
        assertTrue(result);
        assertFalse(currenciesRepository.existsById(1L));
    }
    @Test
    @DirtiesContext
    @Transactional
    public void testAddCurrency() {
        // Given
        CurrenciesDTO currencyDTO = new CurrenciesDTO();
        currencyDTO.setCurrencyId(1L);
        currencyDTO.setCurrencyCode("USD");

        // When
        CurrenciesDTO result = currenciesService.addCurrency(currencyDTO);

        // Then
        assertNotNull(result);
        assertEquals(currencyDTO.getCurrencyCode(), result.getCurrencyCode());
        assertEquals(currencyDTO.getCurrencyId(), result.getCurrencyId());

    }
    @Test
    @DirtiesContext
    @Transactional
    public void testGetCurrencyById_WhenCurrencyExists() {
        // Given
        Currencies currency = new Currencies();
        currency.setCurrencyId(1L);
        currency.setCurrencyCode("USD");
        currenciesRepository.save(currency);

        // When
        Optional<CurrenciesDTO> result = currenciesService.getCurrencyById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(currency.getCurrencyCode(), result.get().getCurrencyCode());
        assertEquals(currency.getCurrencyId(), result.get().getCurrencyId());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void testGetCurrencyById_WhenCurrencyDoesNotExist() {

        // When
        Optional<CurrenciesDTO> result = currenciesService.getCurrencyById(2L);

        // Then
        assertTrue(result.isEmpty());
    }
    @Test
    @DirtiesContext
    @Transactional
    public void testGetAllCurrencies_WhenCurrenciesExist() {
        // Given
        Currencies currency1 = new Currencies();
        currency1.setCurrencyId(1L);
        currency1.setCurrencyCode("USD");

        Currencies currency2 = new Currencies();
        currency2.setCurrencyId(2L);
        currency2.setCurrencyCode("EUR");

        currenciesRepository.saveAll(Arrays.asList(currency1, currency2));

        // When
        List<CurrenciesDTO> result = currenciesService.getAllCurrencies();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    @DirtiesContext
    @Transactional
    public void testGetAllCurrencies_WhenNoCurrenciesExist() {
        // Given that there are no currencies in the repository

        // When
        List<CurrenciesDTO> result = currenciesService.getAllCurrencies();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}
