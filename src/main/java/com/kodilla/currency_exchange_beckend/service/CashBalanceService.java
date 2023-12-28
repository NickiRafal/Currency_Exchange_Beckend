package com.kodilla.currency_exchange_beckend.service;

import com.kodilla.currency_exchange_beckend.Repository.CashBalanceRepository;
import com.kodilla.currency_exchange_beckend.domain.CashBalance;
import com.kodilla.currency_exchange_beckend.domain.CashBalanceDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CashBalanceService {

    private final CashBalanceRepository repository;
    private final ModelMapper modelMapper;

    public List<CashBalanceDTO> getAllCashBalances() {
        try {
            List<CashBalance> cashBalances = repository.findAll();
            return cashBalances.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Handle exception accordingly
            throw new RuntimeException("Failed to retrieve cash balances", e);
        }
    }

    public Optional<CashBalanceDTO> getCashBalanceByCurrencyCode(String currencyCode) {
        try {
            Optional<CashBalance> cashBalance = repository.findByCurrencyCode(currencyCode);
            return cashBalance.map(this::convertToDto);
        } catch (Exception e) {
            // Handle exception accordingly
            throw new RuntimeException("Failed to retrieve cash balance by currency code", e);
        }
    }

    public CashBalanceDTO addOrUpdateCashBalance(CashBalanceDTO cashBalanceDTO) {
        try {
            CashBalance cashBalance = convertToEntity(cashBalanceDTO);
            return convertToDto(repository.save(cashBalance));
        } catch (Exception e) {
            // Handle exception accordingly
            throw new RuntimeException("Failed to add or update cash balance", e);
        }
    }

    public boolean deleteCashBalanceByCurrencyCode(String currencyCode) {
        try {
            Optional<CashBalance> cashBalance = repository.findByCurrencyCode(currencyCode);
            if (cashBalance.isPresent()) {
                repository.delete(cashBalance.get());
                return true;
            }
            return false;
        } catch (Exception e) {
            // Handle exception accordingly
            throw new RuntimeException("Failed to delete cash balance by currency code", e);
        }
    }

    private CashBalanceDTO convertToDto(CashBalance cashBalance) {
        return modelMapper.map(cashBalance, CashBalanceDTO.class);
    }

    private CashBalance convertToEntity(CashBalanceDTO cashBalanceDTO) {
        return modelMapper.map(cashBalanceDTO, CashBalance.class);
    }

    public CashBalanceDTO updateCashBalance(String currencyCode, CashBalanceDTO cashBalanceDTO) {
        try {
            Optional<CashBalance> cashBalanceOptional = repository.findById(currencyCode);

            if (cashBalanceOptional.isPresent()) {
                CashBalance cashBalance = cashBalanceOptional.get();

                modelMapper.map(cashBalanceDTO, cashBalance);
                cashBalance.setLastUpdated(LocalDateTime.now());

                repository.save(cashBalance);

                return modelMapper.map(cashBalance, CashBalanceDTO.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle exception accordingly
            throw new RuntimeException("Failed to update cash balance", e);
        }
    }
}
