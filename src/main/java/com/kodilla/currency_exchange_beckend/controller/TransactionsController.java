package com.kodilla.currency_exchange_beckend.controller;

import com.kodilla.currency_exchange_beckend.domain.TransactionsDTO;
import com.kodilla.currency_exchange_beckend.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactionsService transactionsService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<TransactionsDTO>> getAllTransactions() {
        try {
            List<TransactionsDTO> transactions = transactionsService.getAllTransactions().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionsDTO> getTransactionById(@PathVariable Long transactionId) {
        try {
            Optional<TransactionsDTO> transaction = Optional.ofNullable(transactionsService.getTransactionById(transactionId));
            return transaction.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/add")
    public ResponseEntity<TransactionsDTO> addTransaction(@RequestBody TransactionsDTO transactionDTO) {
        try {
            TransactionsDTO addedTransaction = transactionsService.addTransaction(transactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(addedTransaction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionsDTO> updateTransaction(@PathVariable Long transactionId, @RequestBody TransactionsDTO updatedTransactionDTO) {
        try {
            Optional<TransactionsDTO> updatedTransaction = transactionsService.updateTransaction(transactionId, updatedTransactionDTO);
            return updatedTransaction.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        try {
            boolean deleted = transactionsService.deleteTransaction(transactionId);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private TransactionsDTO convertToDto(TransactionsDTO transaction) {
        return modelMapper.map(transaction, TransactionsDTO.class);
    }
}
