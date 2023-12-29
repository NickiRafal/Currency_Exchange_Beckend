package com.kodilla.currency_exchange_beckend.controller;

import com.kodilla.currency_exchange_beckend.domain.CurrenciesDTO;
import com.kodilla.currency_exchange_beckend.service.CurrenciesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class CurrenciesControllerTest {

    @Mock
    private CurrenciesService currenciesService;

    @InjectMocks
    private CurrenciesController currenciesController;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }



    @Test
    public void getAllCurrencies_ReturnsInternalServerError_WhenServiceThrowsException() {
        // Given
        when(currenciesService.getAllCurrencies()).thenThrow(new RuntimeException("Test Exception"));

        // When
        ResponseEntity<List<CurrenciesDTO>> response = currenciesController.getAllCurrencies();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }
    @Test
    public void getCurrencyById_ShouldReturnCurrency() {
        // Given
        Long currencyId = 1L;
        CurrenciesDTO currencyDTO = new CurrenciesDTO();
        currencyDTO.setCurrencyCode("USD");
        currencyDTO.setCurrencyName("US Dollar");

        when(currenciesService.getCurrencyById(currencyId)).thenReturn(Optional.of(currencyDTO));

        // When
        ResponseEntity<CurrenciesDTO> response = currenciesController.getCurrencyById(currencyId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("USD", response.getBody().getCurrencyCode());
        assertEquals("US Dollar", response.getBody().getCurrencyName());
    }

    @Test
    public void getCurrencyById_ShouldReturnNotFound() {
        // Given
        Long currencyId = 1L;

        when(currenciesService.getCurrencyById(currencyId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<CurrenciesDTO> response = currenciesController.getCurrencyById(currencyId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getCurrencyById_ShouldReturnInternalServerError() {
        // Given
        Long currencyId = 1L;

        when(currenciesService.getCurrencyById(currencyId)).thenThrow(new RuntimeException());

        // When
        ResponseEntity<CurrenciesDTO> response = currenciesController.getCurrencyById(currencyId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void addCurrency_ShouldReturnCreated() {
        // Given
        CurrenciesDTO currencyDTO = new CurrenciesDTO();
        currencyDTO.setCurrencyCode("USD");
        currencyDTO.setCurrencyName("US Dollar");

        when(currenciesService.addCurrency(currencyDTO)).thenReturn(currencyDTO);

        // When
        ResponseEntity<CurrenciesDTO> response = currenciesController.addCurrency(currencyDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("USD", response.getBody().getCurrencyCode());
        assertEquals("US Dollar", response.getBody().getCurrencyName());
    }

    @Test
    public void addCurrency_ShouldReturnInternalServerError() {
        // Given
        CurrenciesDTO currencyDTO = new CurrenciesDTO();
        currencyDTO.setCurrencyCode("USD");
        currencyDTO.setCurrencyName("US Dollar");

        when(currenciesService.addCurrency(currencyDTO)).thenReturn(null);

        // When
        ResponseEntity<CurrenciesDTO> response = currenciesController.addCurrency(currencyDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void addCurrency_ShouldReturnInternalServerErrorOnException() {
        // Given
        CurrenciesDTO currencyDTO = new CurrenciesDTO();
        currencyDTO.setCurrencyCode("USD");
        currencyDTO.setCurrencyName("US Dollar");

        when(currenciesService.addCurrency(currencyDTO)).thenThrow(new RuntimeException());

        // When
        ResponseEntity<CurrenciesDTO> response = currenciesController.addCurrency(currencyDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void updateCurrency_ShouldReturnUpdatedCurrency() {
        // Given
        Long id = 1L;
        CurrenciesDTO updatedCurrencyDTO = new CurrenciesDTO();
        updatedCurrencyDTO.setCurrencyCode("USD");
        updatedCurrencyDTO.setCurrencyName("US Dollar");

        when(currenciesService.existsById(id)).thenReturn(true);
        when(currenciesService.updateCurrency(id, updatedCurrencyDTO)).thenReturn(updatedCurrencyDTO);

        // When
        ResponseEntity<CurrenciesDTO> response = currenciesController.updateCurrency(id, updatedCurrencyDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("USD", response.getBody().getCurrencyCode());
        assertEquals("US Dollar", response.getBody().getCurrencyName());
    }

    @Test
    public void updateCurrency_ShouldReturnInternalServerError() {
        // Given
        Long id = 1L;
        CurrenciesDTO updatedCurrencyDTO = new CurrenciesDTO();
        updatedCurrencyDTO.setCurrencyCode("USD");
        updatedCurrencyDTO.setCurrencyName("US Dollar");

        when(currenciesService.existsById(id)).thenReturn(true);
        when(currenciesService.updateCurrency(id, updatedCurrencyDTO)).thenReturn(null);

        // When
        ResponseEntity<CurrenciesDTO> response = currenciesController.updateCurrency(id, updatedCurrencyDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void updateCurrency_ShouldReturnNotFound() {
        // Given
        Long id = 1L;
        CurrenciesDTO updatedCurrencyDTO = new CurrenciesDTO();
        updatedCurrencyDTO.setCurrencyCode("USD");
        updatedCurrencyDTO.setCurrencyName("US Dollar");

        when(currenciesService.existsById(id)).thenReturn(false);

        // When
        ResponseEntity<CurrenciesDTO> response = currenciesController.updateCurrency(id, updatedCurrencyDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateCurrency_ShouldReturnInternalServerErrorOnException() {
        // Given
        Long id = 1L;
        CurrenciesDTO updatedCurrencyDTO = new CurrenciesDTO();
        updatedCurrencyDTO.setCurrencyCode("USD");
        updatedCurrencyDTO.setCurrencyName("US Dollar");

        when(currenciesService.existsById(id)).thenReturn(true);
        when(currenciesService.updateCurrency(id, updatedCurrencyDTO)).thenThrow(new RuntimeException());

        // When
        ResponseEntity<CurrenciesDTO> response = currenciesController.updateCurrency(id, updatedCurrencyDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void deleteCurrency_ShouldDeleteCurrency() {
        // Given
        Long id = 1L;
        when(currenciesService.deleteCurrency(id)).thenReturn(true);

        // When
        ResponseEntity<Void> response = currenciesController.deleteCurrency(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteCurrency_ShouldReturnNotFound() {
        // Given
        Long id = 1L;
        when(currenciesService.deleteCurrency(id)).thenReturn(false);

        // When
        ResponseEntity<Void> response = currenciesController.deleteCurrency(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteCurrency_ShouldReturnInternalServerErrorOnException() {
        // Given
        Long id = 1L;
        when(currenciesService.deleteCurrency(id)).thenThrow(new RuntimeException());

        // When
        ResponseEntity<Void> response = currenciesController.deleteCurrency(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

