package com.lhv.test.customer.repository;

import com.lhv.test.customer.model.Customer;
import com.lhv.test.customer.port.FetchCustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class FetchCustomerAdapter implements FetchCustomerPort {
    private final CustomerRepository customerRepository;
    private final CustomerEntityMapper mapper;

    @Override
    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id)
                .map(mapper::toDomain);
    }
}