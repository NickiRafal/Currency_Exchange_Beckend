package com.kodilla.currency_exchange_beckend.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customers {
    @Id
    @GeneratedValue
    private Long customerId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Total spent value must be provided")
    private BigDecimal totalSpent;

    private LocalDate registrationDate;
    @NotNull
    private String password;
    @NotNull
    private String login;

    private Boolean status;

}
