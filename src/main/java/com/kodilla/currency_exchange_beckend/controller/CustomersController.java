package com.kodilla.currency_exchange_beckend.controller;

import com.kodilla.currency_exchange_beckend.domain.CustomersDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kodilla.currency_exchange_beckend.service.CustomersService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomersController {

    private final CustomersService customersService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CustomersDTO>> getAllCustomers() {
        try {
            List<CustomersDTO> customers = customersService.getAllCustomers().stream()
                    .map(customer -> modelMapper.map(customer, CustomersDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CustomersDTO> createCustomer(@RequestBody CustomersDTO customerDTO) {
        try {
            if (customersService.isEmailAlreadyRegistered(customerDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            CustomersDTO createdCustomer = customersService.createCustomer(customerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    @PostMapping("/log")
//    public ResponseEntity <CustomersDTO> login (@RequestBody CustomersDTO customersDTO){
//
//    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomersDTO> getCustomerById(@PathVariable Long id) {
        try {
            CustomersDTO customer = customersService.getCustomerById(id);
            return customer != null ?
                    ResponseEntity.ok(customer) :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<CustomersDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomersDTO customerDTO) {
        try {
            CustomersDTO updatedCustomer = customersService.updateCustomer(id, customerDTO);
            return updatedCustomer != null ?
                    ResponseEntity.ok(updatedCustomer) :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            boolean isDeleted = customersService.deleteCustomer(id);
            return isDeleted ?
                    ResponseEntity.noContent().build() :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("add/check-email")
    public ResponseEntity<CheckEmailResponse> checkEmail(@RequestBody CustomersDTO customersDTO) {
        System.out.println("Received request with email: " + customersDTO.getEmail());
        // Sprawdź, czy istnieje już klient o podanym adresie e-mail
        boolean isEmailTaken = customersService.isEmailAlreadyRegistered(customersDTO.getEmail());
        System.out.println(isEmailTaken);
        // Utwórz obiekt odpowiedzi
        CheckEmailResponse response = new CheckEmailResponse();
        response.setEmailTaken(isEmailTaken);

        // Zwróć odpowiedź z treścią
        if (isEmailTaken) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    // Dodatkowy prosty obiekt do przechowywania informacji o zajętości adresu e-mail
    public class CheckEmailResponse {
        private boolean emailTaken;

        public boolean isEmailTaken() {
            return emailTaken;
        }

        public void setEmailTaken(boolean emailTaken) {
            this.emailTaken = emailTaken;
        }
    }

}
