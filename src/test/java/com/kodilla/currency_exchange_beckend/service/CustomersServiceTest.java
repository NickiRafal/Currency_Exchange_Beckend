package com.kodilla.currency_exchange_beckend.service;
import com.kodilla.currency_exchange_beckend.Repository.CustomersRepository;
import com.kodilla.currency_exchange_beckend.domain.Customers;
import com.kodilla.currency_exchange_beckend.domain.CustomersDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomersServiceTest {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomersService customersService;

    @Test
    @DirtiesContext
    @Transactional
    public void testGetCustomerById_WhenCustomerExists() {
        // Given
        Customers customer = new Customers();
        customer.setCustomerId(1L);
        customer.setFirstName("John ");
        customer.setLastName("Doe");

        customersRepository.save(customer);

        // When
        CustomersDTO result = customersService.getCustomerById(1L);

        // Then
        assertNotNull(result);
        assertEquals(customer.getFirstName(), result.getFirstName());
        assertEquals(customer.getCustomerId(), result.getCustomerId());
        assertEquals(customer.getLastName(),result.getLastName());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void testGetCustomerById_WhenCustomerDoesNotExist() {
        // Given

        // When & Then
        assertThrows(RuntimeException.class, () -> customersService.getCustomerById(2L));
    }
    @Test
    @DirtiesContext
    @Transactional
    public void testGetAllCustomers_WhenCustomersExist() {
        // Given
        Customers customer1 = new Customers();
        customer1.setCustomerId(1L);
        customer1.setFirstName("John");

        Customers customer2 = new Customers();
        customer2.setCustomerId(2L);
        customer2.setFirstName("Jane");

        customersRepository.saveAll(Arrays.asList(customer1, customer2));

        // When
        List<CustomersDTO> result = customersService.getAllCustomers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    @DirtiesContext
    @Transactional
    public void testGetAllCustomers_WhenNoCustomersExist() {
        // Given

        // When
        List<CustomersDTO> result = customersService.getAllCustomers();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    @DirtiesContext
    @Transactional
    public void testCreateCustomer() {
        // Given
        CustomersDTO customerDTO = new CustomersDTO();
        customerDTO.setCustomerId(1L);
        customerDTO.setFirstName("John");

        // When
        CustomersDTO result = customersService.createCustomer(customerDTO);

        // Then
        assertNotNull(result);
        assertEquals(customerDTO.getFirstName(), result.getFirstName());
        assertEquals(customerDTO.getCustomerId(), result.getCustomerId());

        Customers createdCustomer = customersRepository.findById(result.getCustomerId()).orElse(null);
        assertNotNull(createdCustomer);
        assertEquals(customerDTO.getFirstName(), createdCustomer.getFirstName());
        assertEquals(customerDTO.getCustomerId(), createdCustomer.getCustomerId());
    }
    @Test
    @DirtiesContext
    @Transactional
    public void testUpdateCustomer_WhenCustomerExists() {
        // Given
        Customers existingCustomer = new Customers();
        existingCustomer.setCustomerId(1L);
        existingCustomer.setFirstName("John");
        customersRepository.save(existingCustomer);

        CustomersDTO updatedCustomerDTO = new CustomersDTO();
        updatedCustomerDTO.setCustomerId(1L);
        updatedCustomerDTO.setFirstName("Jane");

        // When
        CustomersDTO result = customersService.updateCustomer(1L, updatedCustomerDTO);

        // Then
        assertNotNull(result);
        assertEquals(updatedCustomerDTO.getFirstName(), result.getFirstName());
        assertEquals(updatedCustomerDTO.getCustomerId(), result.getCustomerId());

        Customers updatedCustomer = customersRepository.findById(result.getCustomerId()).orElse(null);
        assertNotNull(updatedCustomer);
        assertEquals(updatedCustomerDTO.getFirstName(), updatedCustomer.getFirstName());
        assertEquals(updatedCustomerDTO.getCustomerId(), updatedCustomer.getCustomerId());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void testUpdateCustomer_WhenCustomerDoesNotExist() {
        // Given
        CustomersDTO updatedCustomerDTO = new CustomersDTO();
        updatedCustomerDTO.setCustomerId(2L);
        updatedCustomerDTO.setFirstName("Jane Smith");

        // When & Then
        assertThrows(RuntimeException.class, () -> customersService.updateCustomer(2L, updatedCustomerDTO));
    }
    @Test
    @DirtiesContext
    @Transactional
    public void testDeleteCustomer_WhenCustomerExists() {
        // Given
        Customers customer = new Customers();
        customer.setCustomerId(1L);
        customer.setFirstName("John");
        customersRepository.save(customer);

        // When
        boolean result = customersService.deleteCustomer(1L);

        // Then
        assertTrue(result);
        assertFalse(customersRepository.existsById(1L));
    }

    @Test
    @DirtiesContext
    @Transactional
    public void testDeleteCustomer_WhenCustomerDoesNotExist() {
        // Given

        // When
        boolean result = customersService.deleteCustomer(8L);

        // Then
        assertFalse(result);
    }
}
