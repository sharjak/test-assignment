package com.lhv.test.customer.repository;

import com.lhv.test.customer.model.Customer;
import com.lhv.test.customer.port.SaveCustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
class SaveCustomerAdapter implements SaveCustomerPort {
    private final CustomerRepository customerRepository;
    private final CustomerEntityMapper mapper;

    @Override
    public Customer save(Customer customer) {
        var entity = mapper.toEntity(customer);
        var entityWithId = customerRepository.save(entity);
        return mapper.toDomain(entityWithId);
    }

    @Override
    public void delete(UUID id) {
        customerRepository.deleteById(id);
    }
}
