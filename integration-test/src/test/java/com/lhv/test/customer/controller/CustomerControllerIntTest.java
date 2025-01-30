package com.lhv.test.customer.controller;

import com.lhv.test.IntegrationTest;
import com.lhv.test.customer.model.Customer;
import com.lhv.test.customer.service.CustomerService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerControllerIntTest extends IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String EMAIL = "john.doe@example.com";

    @Nested
    class when_fetch_customer {

        @Test
        void given_customer_with_id_exists_then_return_customer() {
            var customer = Customer.builder().firstName(FIRST_NAME).lastName(LAST_NAME).email(EMAIL).build();
            customer = customerService.createCustomer(customer);

            ResponseEntity<CustomerDto> response = restTemplate.getForEntity("/customers/" + customer.getId(), CustomerDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().id()).isEqualTo(customer.getId());
            assertThat(response.getBody().firstName()).isEqualTo(FIRST_NAME);
            assertThat(response.getBody().lastName()).isEqualTo(LAST_NAME);
            assertThat(response.getBody().email()).isEqualTo(EMAIL);
        }
    }

    @Nested
    class when_create_customer {

        @Test
        void given_valid_customer_then_creates_and_returns_customer_with_id() {
            SaveCustomerDto request = new SaveCustomerDto(FIRST_NAME, LAST_NAME, EMAIL);

            ResponseEntity<CustomerDto> response = restTemplate.postForEntity("/customers", request, CustomerDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().id()).isNotNull();
            assertThat(response.getBody().firstName()).isEqualTo(FIRST_NAME);
            assertThat(response.getBody().lastName()).isEqualTo(LAST_NAME);
            assertThat(response.getBody().email()).isEqualTo(EMAIL);
        }
    }

    @Nested
    class when_update_customer {

        @Test
        void given_valid_customer_then_updates_and_returns_customer() {
            var customer = Customer.builder().firstName(FIRST_NAME).lastName(LAST_NAME).email(EMAIL).build();
            customer = customerService.createCustomer(customer);

            SaveCustomerDto request = new SaveCustomerDto("Updated", "Customer", "updated@example.com");

            ResponseEntity<CustomerDto> response = restTemplate.exchange(
                    "/customers/" + customer.getId(),
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    CustomerDto.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().id()).isEqualTo(customer.getId());
            assertThat(response.getBody().firstName()).isEqualTo("Updated");
            assertThat(response.getBody().lastName()).isEqualTo("Customer");
            assertThat(response.getBody().email()).isEqualTo("updated@example.com");
        }
    }

    @Nested
    class when_delete_customer {

        @Test
        void given_existing_customer_then_deletes_successfully() {
            var customer = Customer.builder().firstName(FIRST_NAME).lastName(LAST_NAME).email(EMAIL).build();
            customer = customerService.createCustomer(customer);

            ResponseEntity<Void> response = restTemplate.exchange(
                    "/customers/" + customer.getId(),
                    HttpMethod.DELETE,
                    HttpEntity.EMPTY,
                    Void.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            ResponseEntity<CustomerDto> fetchResponse = restTemplate.getForEntity("/customers/" + customer.getId(), CustomerDto.class);
            assertThat(fetchResponse.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
