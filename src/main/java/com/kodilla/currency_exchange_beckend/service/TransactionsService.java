package com.kodilla.currency_exchange_beckend.service;

import com.kodilla.currency_exchange_beckend.Repository.TransactionsRepository;
import com.kodilla.currency_exchange_beckend.domain.Transactions;
import com.kodilla.currency_exchange_beckend.domain.TransactionsDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final ModelMapper modelMapper;

    public List<TransactionsDTO> getAllTransactions() {
        List<Transactions> transactions = transactionsRepository.findAll();
        return transactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TransactionsDTO getTransactionById(Long id) {
        Transactions transaction = transactionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return modelMapper.map(transaction, TransactionsDTO.class);
    }

    public TransactionsDTO addTransaction(TransactionsDTO transactionDTO) {
        Transactions transaction = convertToEntity(transactionDTO);
        Transactions savedTransaction = transactionsRepository.save(transaction);
        return modelMapper.map(savedTransaction, TransactionsDTO.class);
    }

    public Optional<TransactionsDTO> updateTransaction(Long id, TransactionsDTO updatedTransactionDTO) {
        return transactionsRepository.findById(id)
                .map(existingTransaction -> {
                    updatedTransactionDTO.setTransactionId(id);
                    Transactions updatedTransaction = convertToEntity(updatedTransactionDTO);
                    Transactions savedTransaction = transactionsRepository.save(updatedTransaction);
                    return modelMapper.map(savedTransaction, TransactionsDTO.class);
                });
    }

    public boolean deleteTransaction(Long id) {
        if (transactionsRepository.existsById(id)) {
            transactionsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TransactionsDTO convertToDto(Transactions transaction) {
        return modelMapper.map(transaction, TransactionsDTO.class);
    }

    private Transactions convertToEntity(TransactionsDTO transactionDTO) {
        return modelMapper.map(transactionDTO, Transactions.class);
    }
}
