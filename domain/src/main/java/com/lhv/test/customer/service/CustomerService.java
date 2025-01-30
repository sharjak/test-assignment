package com.lhv.test.customer.service;

import com.lhv.test.customer.model.Customer;

import java.util.UUID;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    Customer createCustomer(Customer customer);

    Customer fetchCustomer(UUID id);

    void deleteCustomer(UUID id);


}
