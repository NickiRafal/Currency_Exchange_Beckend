package com.kodilla.currency_exchange_beckend.controller;

import com.kodilla.currency_exchange_beckend.domain.Currencies;
import com.kodilla.currency_exchange_beckend.domain.CurrenciesDTO;
import com.kodilla.currency_exchange_beckend.service.CurrenciesService;
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
@RequestMapping("/v1/currencies")
@RequiredArgsConstructor
public class CurrenciesController {

    private final CurrenciesService currenciesService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public ResponseEntity<List<CurrenciesDTO>> getAllCurrencies() {
        try {
            List<CurrenciesDTO> currencies = currenciesService.getAllCurrencies();
            return ResponseEntity.ok(currencies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<CurrenciesDTO> getCurrencyById(@PathVariable Long id) {
        try {
            Optional<CurrenciesDTO> currencyDTO = currenciesService.getCurrencyById(id);
            return currencyDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CurrenciesDTO> addCurrency(@RequestBody CurrenciesDTO currencyDTO) {
        try {
            CurrenciesDTO addedCurrencyDTO = currenciesService.addCurrency(currencyDTO);
            return addedCurrencyDTO != null ?
                    ResponseEntity.status(HttpStatus.CREATED).body(addedCurrencyDTO) :
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<CurrenciesDTO> updateCurrency(@PathVariable Long id, @RequestBody CurrenciesDTO updatedCurrencyDTO) {
        try {
            if (currenciesService.existsById(id)) {
                updatedCurrencyDTO.setCurrencyId(id);
                CurrenciesDTO updatedCurrency = currenciesService.updateCurrency(id, updatedCurrencyDTO);
                return updatedCurrency != null ?
                        ResponseEntity.ok(updatedCurrency) :
                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        try {
            boolean isDeleted = currenciesService.deleteCurrency(id);
            return isDeleted ?
                    ResponseEntity.noContent().build() :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private CurrenciesDTO convertToDto(Currencies currency) {
        return modelMapper.map(currency, CurrenciesDTO.class);
    }
}
