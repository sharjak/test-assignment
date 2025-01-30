package com.lhv.test.customer.service;

import com.lhv.test.UnitTest;
import com.lhv.test.common.exception.BusinessViolationException;
import com.lhv.test.common.exception.UnexpectedStateException;
import com.lhv.test.customer.model.Customer;
import com.lhv.test.customer.port.FetchCustomerPort;
import com.lhv.test.customer.port.SaveCustomerPort;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerServiceUnitTest extends UnitTest {
    private static final UUID ID = UUID.randomUUID();
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String EMAIL = "john@example.com";
    private static final String INVALID_EMAIL = "invalid-email";

    private final SaveCustomerPort saveCustomerPort = mock(SaveCustomerPort.class);
    private final FetchCustomerPort fetchCustomerPort = mock(FetchCustomerPort.class);
    private final CustomerServiceImpl customerService = new CustomerServiceImpl(saveCustomerPort, fetchCustomerPort);

    @Nested
    class when_fetch_customer {

        @Test
        void given_customer_exists_then_return_customer() {
            var expectedCustomer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).email(EMAIL).build();
            when(fetchCustomerPort.findById(ID)).thenReturn(Optional.of(expectedCustomer));

            var result = customerService.fetchCustomer(ID);

            assertThat(result).isEqualTo(expectedCustomer);
            verify(fetchCustomerPort).findById(ID);
        }

        @Test
        void given_customer_does_not_exist_then_throw_unexpected_state_exception() {
            when(fetchCustomerPort.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.fetchCustomer(ID))
                    .isInstanceOf(UnexpectedStateException.class)
                    .hasMessage("Customer with ID " + ID + " not found");
            verify(fetchCustomerPort).findById(ID);
        }
    }

    @Nested
    class when_create_customer {

        @Test
        void given_valid_customer_then_return_saved_customer() {
            var inputCustomer = Customer.builder().firstName(FIRST_NAME).lastName(LAST_NAME).email(EMAIL).build();
            var savedCustomer = inputCustomer.toBuilder().id(UUID.randomUUID()).build();
            when(saveCustomerPort.save(inputCustomer)).thenReturn(savedCustomer);

            var result = customerService.createCustomer(inputCustomer);

            assertThat(result).isEqualTo(savedCustomer);
            verify(saveCustomerPort).save(inputCustomer);
        }

        @Test
        void given_invalid_customer_then_throw_exception() {
            var invalidCustomer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).email(INVALID_EMAIL).build();

            assertThatThrownBy(() -> customerService.createCustomer(invalidCustomer))
                    .isInstanceOf(BusinessViolationException.class);
        }
    }

    @Nested
    class when_update_customer {

        @Test
        void given_existing_customer_then_update_and_return_customer() {
            var existingCustomer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).email(EMAIL).build();
            when(fetchCustomerPort.findById(ID)).thenReturn(Optional.of(existingCustomer));
            when(saveCustomerPort.save(existingCustomer)).thenReturn(existingCustomer);

            var result = customerService.saveCustomer(existingCustomer);

            assertThat(result).isEqualTo(existingCustomer);
            verify(fetchCustomerPort).findById(ID);
            verify(saveCustomerPort).save(existingCustomer);
        }

        @Test
        void given_non_existing_customer_then_throw_exception() {
            var nonExistingCustomer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).email(EMAIL).build();
            when(fetchCustomerPort.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.saveCustomer(nonExistingCustomer))
                    .isInstanceOf(UnexpectedStateException.class)
                    .hasMessage("Customer with ID " + ID + " not found");
            verify(fetchCustomerPort).findById(ID);
        }
    }

    @Nested
    class when_delete_customer {

        @Test
        void given_existing_customer_then_delete() {
            customerService.deleteCustomer(ID);

            verify(saveCustomerPort).delete(ID);
        }
    }
}
