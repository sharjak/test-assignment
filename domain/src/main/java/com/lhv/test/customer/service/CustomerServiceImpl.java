package com.lhv.test.customer.service;

import com.lhv.test.common.exception.UnexpectedStateException;
import com.lhv.test.customer.model.Customer;
import com.lhv.test.customer.port.FetchCustomerPort;
import com.lhv.test.customer.port.SaveCustomerPort;
import com.lhv.test.customer.util.CustomerValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class CustomerServiceImpl implements CustomerService {
    private final SaveCustomerPort saveCustomerPort;
    private final FetchCustomerPort fetchCustomerPort;

    @Override
    public Customer fetchCustomer(UUID id) {
        return fetchCustomerPort.findById(id)
                .orElseThrow(() -> new UnexpectedStateException("Customer with ID " + id + " not found"));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        CustomerValidationUtil.validateForCreate(customer);
        return saveCustomerPort.save(customer);
    }

    @Override
    public void deleteCustomer(UUID id) {
        saveCustomerPort.delete(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerValidationUtil.validateForUpdate(customer);
        fetchCustomerPort.findById(customer.getId())
                .orElseThrow(() -> new UnexpectedStateException("Customer with ID " + customer.getId() + " not found"));
        return saveCustomerPort.save(customer);
    }
}
