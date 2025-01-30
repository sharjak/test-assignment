package com.lhv.test.customer.port;

import com.lhv.test.customer.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface FetchCustomerPort {

    Optional<Customer> findById(UUID id);
}
