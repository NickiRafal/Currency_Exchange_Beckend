package com.kodilla.currency_exchange_beckend.service;

import com.kodilla.currency_exchange_beckend.Repository.CurrenciesRepository;
import com.kodilla.currency_exchange_beckend.domain.Currencies;
import com.kodilla.currency_exchange_beckend.domain.CurrenciesDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CurrenciesService {

    private final CurrenciesRepository repository;
    private final ModelMapper modelMapper;

    public List<CurrenciesDTO> getAllCurrencies() {
        try {
            Iterable<Currencies> currenciesIterable = repository.findAll();
            List<CurrenciesDTO> currencies = StreamSupport.stream(currenciesIterable.spliterator(), false)
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return currencies;
        } catch (Exception e) {
            // Handle exception accordingly
            throw new RuntimeException("Failed to retrieve all currencies", e);
        }
    }

    public Optional<CurrenciesDTO> getCurrencyById(Long id) {
        try {
            Optional<Currencies> currency = repository.findById(id);
            return currency.map(this::convertToDto);
        } catch (Exception e) {
            // Handle exception accordingly
            throw new RuntimeException("Failed to retrieve currency by ID", e);
        }
    }

    public CurrenciesDTO addCurrency(CurrenciesDTO currencyDTO) {
        try {
            Currencies currency = convertToEntity(currencyDTO);
            return convertToDto(repository.save(currency));
        } catch (Exception e) {
            // Handle exception accordingly
            throw new RuntimeException("Failed to add currency", e);
        }
    }

    public CurrenciesDTO updateCurrency(Long id, CurrenciesDTO updatedCurrencyDTO) {
        try {
            if (repository.existsById(id)) {
                updatedCurrencyDTO.setCurrencyId(id);
                Currencies updatedCurrency = convertToEntity(updatedCurrencyDTO);
                updatedCurrency = repository.save(updatedCurrency);
                return convertToDto(updatedCurrency);
            }
            return null;
        } catch (Exception e) {
            // Handle exception accordingly
            throw new RuntimeException("Failed to update currency", e);
        }
    }

    public boolean deleteCurrency(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            // Handle exception accordingly
            throw new RuntimeException("Failed to delete currency", e);
        }
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    private CurrenciesDTO convertToDto(Currencies currency) {
        return modelMapper.map(currency, CurrenciesDTO.class);
    }

    private Currencies convertToEntity(CurrenciesDTO currencyDTO) {
        return modelMapper.map(currencyDTO, Currencies.class);
    }
}
