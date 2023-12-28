package com.kodilla.currency_exchange_beckend.controller;

import com.kodilla.currency_exchange_beckend.domain.CashBalanceDTO;
import com.kodilla.currency_exchange_beckend.service.CashBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/cash-balances")
@RequiredArgsConstructor
public class CashBalanceController {

    private final CashBalanceService cashBalanceService;

    @GetMapping("/all")
    public ResponseEntity<List<CashBalanceDTO>> getAllCashBalances() {
        try {
            List<CashBalanceDTO> cashBalances = cashBalanceService.getAllCashBalances();
            return ResponseEntity.ok(cashBalances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{currencyCode}")
    public ResponseEntity<CashBalanceDTO> getCashBalanceByCurrencyCode(@PathVariable String currencyCode) {
        try {
            CashBalanceDTO cashBalanceDTO = cashBalanceService.getCashBalanceByCurrencyCode(currencyCode).orElse(null);
            return cashBalanceDTO != null ?
                    ResponseEntity.ok(cashBalanceDTO) :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CashBalanceDTO> addOrUpdateCashBalance(@RequestBody CashBalanceDTO cashBalanceDTO) {
        try {
            CashBalanceDTO addedOrUpdatedCashBalance = cashBalanceService.addOrUpdateCashBalance(cashBalanceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedOrUpdatedCashBalance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{currencyCode}/update")
    public ResponseEntity<CashBalanceDTO> updateCashBalance(@PathVariable String currencyCode, @RequestBody CashBalanceDTO cashBalanceDTO) {
        try {
            CashBalanceDTO updatedCashBalance = cashBalanceService.updateCashBalance(currencyCode, cashBalanceDTO);
            return updatedCashBalance != null ?
                    ResponseEntity.ok(updatedCashBalance) :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{currencyCode}")
    public ResponseEntity<Void> deleteCashBalanceByCurrencyCode(@PathVariable String currencyCode) {
        try {
            boolean isDeleted = cashBalanceService.deleteCashBalanceByCurrencyCode(currencyCode);
            return isDeleted ?
                    ResponseEntity.noContent().build() :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
