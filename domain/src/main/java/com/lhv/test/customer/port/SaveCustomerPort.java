package com.lhv.test.customer.port;

import com.lhv.test.customer.model.Customer;

import java.util.UUID;

public interface SaveCustomerPort {

    Customer save(Customer sample);

    void delete(UUID id);
}
