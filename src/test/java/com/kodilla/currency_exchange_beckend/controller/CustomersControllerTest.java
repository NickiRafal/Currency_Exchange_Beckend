package com.kodilla.currency_exchange_beckend.controller;

import com.kodilla.currency_exchange_beckend.domain.CustomersDTO;
import com.kodilla.currency_exchange_beckend.service.CustomersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomersControllerTest {

    @Mock
    private CustomersService customersService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomersController customersController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customersController = new CustomersController(customersService, modelMapper);
    }

    @Test
    public void getAllCustomers_ShouldReturnListOfCustomers() {
        // Given
        List<CustomersDTO> mockCustomers = Collections.singletonList(new CustomersDTO(1L,"Rafal","Nicki","test@test.com", BigDecimal.valueOf(100.00), LocalDate.of(2023,05,06),"Test","Test",false));
        when(customersService.getAllCustomers()).thenReturn(mockCustomers);
        when(modelMapper.map(any(), eq(CustomersDTO.class))).thenReturn(new CustomersDTO(1L,"Rafal","Nicki","test@test.com", BigDecimal.valueOf(100.00), LocalDate.of(2023,05,06),"Test","Test",false));

        // When
        ResponseEntity<List<CustomersDTO>> response = customersController.getAllCustomers();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCustomers.size(), response.getBody().size());

    }

    @Test
    public void getAllCustomers_ShouldReturnInternalServerErrorOnException() {
        // Given
        when(customersService.getAllCustomers()).thenThrow(new RuntimeException());

        // When
        ResponseEntity<List<CustomersDTO>> response = customersController.getAllCustomers();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void createCustomer_ShouldReturnCreatedCustomer() {
        // Given
        CustomersDTO customerDTO = new CustomersDTO(1L,"Rafal","Nicki","test@test.com", BigDecimal.valueOf(100.00), LocalDate.of(2023,05,06),"Test","Test",false);
        CustomersDTO createdCustomerDTO = new CustomersDTO(1L,"Rafal","Nicki","test@test.com", BigDecimal.valueOf(100.00), LocalDate.of(2023,05,06),"Test","Test",false);

        when(customersService.createCustomer(customerDTO)).thenReturn(createdCustomerDTO);

        // When
        ResponseEntity<CustomersDTO> response = customersController.createCustomer(customerDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdCustomerDTO, response.getBody());
    }

    @Test
    public void createCustomer_ShouldReturnInternalServerError() {
        // Given
        CustomersDTO customerDTO = new CustomersDTO(1L,"Rafal","Nicki","test@test.com", BigDecimal.valueOf(100.00), LocalDate.of(2023,05,06),"Test","Test",false);

        when(customersService.createCustomer(customerDTO)).thenThrow(new RuntimeException());

        // When
        ResponseEntity<CustomersDTO> response = customersController.createCustomer(customerDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void getCustomerById_ShouldReturnCustomer() {
        // Given
        Long customerId = 1L;
        CustomersDTO customerDTO = new CustomersDTO(1L,"Rafal","Nicki","test@test.com", BigDecimal.valueOf(100.00), LocalDate.of(2023,05,06),"Test","Test",false);

        when(customersService.getCustomerById(customerId)).thenReturn(customerDTO);

        // When
        ResponseEntity<CustomersDTO> response = customersController.getCustomerById(customerId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());
    }

    @Test
    public void getCustomerById_ShouldReturnNotFound() {
        // Given
        Long customerId = 1L;

        // Ustawienie sytuacji, w której metoda serwisu zwraca null
        when(customersService.getCustomerById(customerId)).thenReturn(null);

        // When
        ResponseEntity<CustomersDTO> response = customersController.getCustomerById(customerId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getCustomerById_ShouldReturnInternalServerError() {
        // Given
        Long customerId = 1L;

        // Ustawienie sytuacji, w której metoda serwisu zwraca wyjątek
        when(customersService.getCustomerById(customerId)).thenThrow(new RuntimeException());

        // When
        ResponseEntity<CustomersDTO> response = customersController.getCustomerById(customerId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void updateCustomer_ShouldReturnUpdatedCustomer() {
        // Given
        Long customerId = 1L;
        CustomersDTO customerDTO = new CustomersDTO(1L,"Rafal","Nicki","test@test.com", BigDecimal.valueOf(100.00), LocalDate.of(2023,05,06),"Test","Test",false);
        CustomersDTO updatedCustomerDTO = new CustomersDTO(1L,"Rafal","Nicki","test@test.com", BigDecimal.valueOf(120.00), LocalDate.of(2023,05,06),"Test","Test",false);

        when(customersService.updateCustomer(customerId, customerDTO)).thenReturn(updatedCustomerDTO);

        // When
        ResponseEntity<CustomersDTO> response = customersController.updateCustomer(customerId, customerDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomerDTO, response.getBody());
    }

    @Test
    public void updateCustomer_ShouldReturnNotFound() {
        // Given
        Long customerId = 1L;
        CustomersDTO customerDTO = new CustomersDTO(1L,"Rafal","Nicki","test@test.com", BigDecimal.valueOf(100.00), LocalDate.of(2023,05,06),"Test","Test",false);

        // Ustawienie sytuacji, w której metoda serwisu zwraca null
        when(customersService.updateCustomer(customerId, customerDTO)).thenReturn(null);

        // When
        ResponseEntity<CustomersDTO> response = customersController.updateCustomer(customerId, customerDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateCustomer_ShouldReturnInternalServerError() {
        // Given
        Long customerId = 1L;
        CustomersDTO customerDTO = new CustomersDTO(1L,"Rafal","Nicki","test@test.com", BigDecimal.valueOf(100.00), LocalDate.of(2023,05,06),"Test","Test",false);

        // Ustawienie sytuacji, w której metoda serwisu zwraca wyjątek
        when(customersService.updateCustomer(customerId, customerDTO)).thenThrow(new RuntimeException());

        // When
        ResponseEntity<CustomersDTO> response = customersController.updateCustomer(customerId, customerDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void deleteCustomer_ShouldReturnNoContent() {
        // Given
        Long customerId = 1L;

        // Ustawienie sytuacji, w której metoda serwisu zwraca true (sukces usuwania)
        when(customersService.deleteCustomer(customerId)).thenReturn(true);

        // When
        ResponseEntity<Void> response = customersController.deleteCustomer(customerId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteCustomer_ShouldReturnNotFound() {
        // Given
        Long customerId = 1L;

        // Ustawienie sytuacji, w której metoda serwisu zwraca false (brak klienta do usunięcia)
        when(customersService.deleteCustomer(customerId)).thenReturn(false);

        // When
        ResponseEntity<Void> response = customersController.deleteCustomer(customerId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteCustomer_ShouldReturnInternalServerError() {
        // Given
        Long customerId = 1L;

        // Ustawienie sytuacji, w której metoda serwisu zwraca wyjątek
        when(customersService.deleteCustomer(customerId)).thenThrow(new RuntimeException());

        // When
        ResponseEntity<Void> response = customersController.deleteCustomer(customerId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
