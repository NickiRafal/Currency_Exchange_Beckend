package com.kodilla.currency_exchange_beckend.service;

import com.kodilla.currency_exchange_beckend.Repository.CustomersRepository;
import com.kodilla.currency_exchange_beckend.domain.Customers;
import com.kodilla.currency_exchange_beckend.domain.CustomersDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomersService {
    private final CustomersRepository repository;
    private final ModelMapper modelMapper;

    public CustomersDTO getCustomerById(Long id) {
        return repository.findById(id)
                .map(customer -> modelMapper.map(customer, CustomersDTO.class))
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public List<CustomersDTO> getAllCustomers() {
        List<Customers> customers = repository.findAll();
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomersDTO.class))
                .collect(Collectors.toList());
    }

    public CustomersDTO createCustomer(CustomersDTO customerDTO) {
        Customers customer = modelMapper.map(customerDTO, Customers.class);
        Customers savedCustomer = repository.save(customer);
        return modelMapper.map(savedCustomer, CustomersDTO.class);
    }

    public CustomersDTO updateCustomer(Long id, CustomersDTO customerDTO) {
        return repository.findById(id)
                .map(existingCustomer -> {
                    modelMapper.map(customerDTO, existingCustomer);
                    Customers updatedCustomer = repository.save(existingCustomer);
                    return modelMapper.map(updatedCustomer, CustomersDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public boolean deleteCustomer(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }return false;
    }

}
