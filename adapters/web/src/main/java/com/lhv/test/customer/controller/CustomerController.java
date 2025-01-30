package com.lhv.test.customer.controller;

import com.lhv.test.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Customer Controller", description = "API for managing customers")
@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerDtoMapper customerDtoMapper;

    @Operation(summary = "Create a new customer", description = "Creates a new customer and returns the customer with id.")
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody @Valid SaveCustomerDto saveCustomerDto) {
        var customer = customerDtoMapper.toDomain(saveCustomerDto);
        customer = customerService.createCustomer(customer);
        var customerDto = customerDtoMapper.toDto(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
    }

    @Operation(summary = "Fetch a customer by ID", description = "Retrieves a customer based on the provided UUID.")
    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> fetchCustomer(@PathVariable UUID id) {
        var customer = customerService.fetchCustomer(id);
        var customerDto = customerDtoMapper.toDto(customer);
        return ResponseEntity.ok(customerDto);
    }

    @Operation(summary = "Update a customer", description = "Updates the customer and returns it.")
    @PutMapping("{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable UUID id,
                                                      @RequestBody @Valid SaveCustomerDto saveCustomerDto) {
        var customer = customerDtoMapper.toDomain(id, saveCustomerDto);
        customer = customerService.saveCustomer(customer);
        var customerDto = customerDtoMapper.toDto(customer);
        return ResponseEntity.ok(customerDto);
    }

    @Operation(summary = "Delete a customer", description = "Deletes the customer.")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}
