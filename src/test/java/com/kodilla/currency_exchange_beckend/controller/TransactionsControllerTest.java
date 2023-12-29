package com.kodilla.currency_exchange_beckend.controller;

import com.kodilla.currency_exchange_beckend.domain.TransactionsDTO;
import com.kodilla.currency_exchange_beckend.service.TransactionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class TransactionsControllerTest {

    @InjectMocks
    private TransactionsController transactionsController;

    @Mock
    private TransactionsService transactionsService;
    @Mock
    private ModelMapper modelMapper;
    private TransactionsDTO dto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
         dto = new TransactionsDTO(
                1L,
                10L,
                LocalDateTime.of(2023, 5, 1, 12, 15),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(800),
                BigDecimal.valueOf(1.25),
                BigDecimal.valueOf(5),
                "completed",
                1L,
                2L
        );
         modelMapper = new ModelMapper();
        transactionsController.setModelMapper(modelMapper);
    }

    @Test
    public void getAllTransactions_ShouldReturnListOfTransactions() {
        // Given
        List<TransactionsDTO> mockTransactions = Collections.singletonList(dto);
        when(transactionsService.getAllTransactions()).thenReturn(mockTransactions);

        // When
        ResponseEntity<List<TransactionsDTO>> response = transactionsController.getAllTransactions();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTransactions.size(), response.getBody().size());
    }

    @Test
    public void getAllTransactions_ShouldReturnInternalServerError() {
        // Given
        when(transactionsService.getAllTransactions()).thenThrow(new RuntimeException());

        // When
        ResponseEntity<List<TransactionsDTO>> response = transactionsController.getAllTransactions();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void getTransactionById_ShouldReturnTransactionIfExists() {
        // Given
        Long transactionId = 1L;
        TransactionsDTO mockTransaction = new TransactionsDTO();
        mockTransaction.setTransactionId(transactionId);

        when(transactionsService.getTransactionById(transactionId)).thenReturn(mockTransaction);

        // When
        ResponseEntity<TransactionsDTO> response = transactionsController.getTransactionById(transactionId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionId, response.getBody().getTransactionId());
    }

    @Test
    public void getTransactionById_ShouldReturnNotFoundForNonExistingTransaction() {
        // Given
        Long transactionId = 1L;
        when(transactionsService.getTransactionById(transactionId)).thenReturn(null);

        // When
        ResponseEntity<TransactionsDTO> response = transactionsController.getTransactionById(transactionId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void getTransactionById_ShouldReturnInternalServerErrorOnException() {
        // Given
        Long transactionId = 1L;
        when(transactionsService.getTransactionById(transactionId)).thenThrow(RuntimeException.class);

        // When
        ResponseEntity<TransactionsDTO> response = transactionsController.getTransactionById(transactionId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    public void addTransaction_ShouldReturnCreated() {
        // Given
        TransactionsDTO transactionDTO = new TransactionsDTO();
        transactionDTO.setTransactionId(1L);

        when(transactionsService.addTransaction(any())).thenReturn(transactionDTO);

        // When
        ResponseEntity<TransactionsDTO> response = transactionsController.addTransaction(transactionDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transactionDTO.getTransactionId(), response.getBody().getTransactionId());
    }

    @Test
    public void addTransaction_ShouldReturnInternalServerErrorOnException() {
        // Given
        TransactionsDTO transactionDTO = new TransactionsDTO();
        when(transactionsService.addTransaction(any())).thenThrow(RuntimeException.class);

        // When
        ResponseEntity<TransactionsDTO> response = transactionsController.addTransaction(transactionDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    public void updateTransaction_ShouldReturnUpdatedTransaction() {
        // Given
        Long transactionId = 1L;
        TransactionsDTO updatedTransactionDTO = new TransactionsDTO();
        updatedTransactionDTO.setTransactionId(transactionId);

        Optional<TransactionsDTO> updatedTransactionOptional = Optional.of(updatedTransactionDTO);

        when(transactionsService.updateTransaction(eq(transactionId), any())).thenReturn(updatedTransactionOptional);

        // When
        ResponseEntity<TransactionsDTO> response = transactionsController.updateTransaction(transactionId, updatedTransactionDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionId, response.getBody().getTransactionId());
    }

    @Test
    public void updateTransaction_ShouldReturnNotFound() {
        // Given
        Long transactionId = 1L;
        TransactionsDTO updatedTransactionDTO = new TransactionsDTO();

        when(transactionsService.updateTransaction(eq(transactionId), any())).thenReturn(Optional.empty());

        // When
        ResponseEntity<TransactionsDTO> response = transactionsController.updateTransaction(transactionId, updatedTransactionDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void updateTransaction_ShouldReturnInternalServerErrorOnException() {
        // Given
        Long transactionId = 1L;
        TransactionsDTO updatedTransactionDTO = new TransactionsDTO();

        when(transactionsService.updateTransaction(eq(transactionId), any())).thenThrow(RuntimeException.class);

        // When
        ResponseEntity<TransactionsDTO> response = transactionsController.updateTransaction(transactionId, updatedTransactionDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    public void deleteTransaction_ShouldReturnNoContent() {
        // Given
        Long transactionId = 1L;
        when(transactionsService.deleteTransaction(transactionId)).thenReturn(true);

        // When
        ResponseEntity<Void> response = transactionsController.deleteTransaction(transactionId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteTransaction_ShouldReturnNotFound() {
        // Given
        Long transactionId = 1L;
        when(transactionsService.deleteTransaction(transactionId)).thenReturn(false);

        // When
        ResponseEntity<Void> response = transactionsController.deleteTransaction(transactionId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteTransaction_ShouldReturnInternalServerErrorOnException() {
        // Given
        Long transactionId = 1L;
        when(transactionsService.deleteTransaction(transactionId)).thenThrow(RuntimeException.class);

        // When
        ResponseEntity<Void> response = transactionsController.deleteTransaction(transactionId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
