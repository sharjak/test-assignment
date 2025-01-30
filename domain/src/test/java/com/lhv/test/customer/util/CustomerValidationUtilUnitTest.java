package com.lhv.test.customer.util;

import com.lhv.test.UnitTest;
import com.lhv.test.common.exception.BusinessViolationException;
import com.lhv.test.customer.model.Customer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerValidationUtilUnitTest extends UnitTest {
    private static final UUID ID = UUID.randomUUID();
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";

    @Nested
    class when_validate_for_create {

        @Test
        void given_valid_customer_then_no_exception() {
            var customer = Customer.builder().firstName(FIRST_NAME).lastName(LAST_NAME).email("john@example.com").build();
            CustomerValidationUtil.validateForCreate(customer);
        }

        @Test
        void given_customer_with_null_id_then_no_exception() {
            var customer = Customer.builder().firstName(FIRST_NAME).lastName(LAST_NAME).email("john@example.com").build();
            CustomerValidationUtil.validateForCreate(customer);
        }

        @Test
        void given_customer_with_non_null_id_then_throw_exception() {
            var customer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).email("john@example.com").build();

            assertThatThrownBy(() -> CustomerValidationUtil.validateForCreate(customer))
                    .isInstanceOf(BusinessViolationException.class)
                    .hasMessage("ID must be null when creating a customer");
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", " ", "   "})
        void given_customer_with_blank_or_null_first_name_then_throw_exception(String blankName) {
            var customer = Customer.builder().firstName(blankName).lastName(LAST_NAME).email("john@example.com").build();

            assertThatThrownBy(() -> CustomerValidationUtil.validateForCreate(customer))
                    .isInstanceOf(BusinessViolationException.class)
                    .hasMessage("First name is required");
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", " ", "   "})
        void given_customer_with_blank_or_null_last_name_then_throw_exception(String blankName) {
            var customer = Customer.builder().firstName(FIRST_NAME).lastName(blankName).email("john@example.com").build();

            assertThatThrownBy(() -> CustomerValidationUtil.validateForCreate(customer))
                    .isInstanceOf(BusinessViolationException.class)
                    .hasMessage("Last name is required");
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", " ", "   "})
        void given_customer_with_blank_or_null_email_then_throw_exception(String blankEmail) {
            var customer = Customer.builder().firstName(FIRST_NAME).lastName(LAST_NAME).email(blankEmail).build();

            assertThatThrownBy(() -> CustomerValidationUtil.validateForCreate(customer))
                    .isInstanceOf(BusinessViolationException.class)
                    .hasMessage("Invalid email format");
        }

        @ParameterizedTest
        @ValueSource(strings = {"invalid-email", "john@.com", "john@com", "@example.com", "john@example"})
        void given_customer_with_invalid_email_then_throw_exception(String invalidEmail) {
            var customer = Customer.builder().firstName(FIRST_NAME).lastName(LAST_NAME).email(invalidEmail).build();

            assertThatThrownBy(() -> CustomerValidationUtil.validateForCreate(customer))
                    .isInstanceOf(BusinessViolationException.class)
                    .hasMessage("Invalid email format");
        }

        @ParameterizedTest
        @ValueSource(strings = {"john.doe@example.com", "user123@mail.com", "valid_email@sub.example.co.uk"})
        void given_customer_with_valid_email_then_no_exception(String validEmail) {
            var customer = Customer.builder().firstName(FIRST_NAME).lastName(LAST_NAME).email(validEmail).build();
            CustomerValidationUtil.validateForCreate(customer);
        }
    }

    @Nested
    class when_validate_for_update {

        @Test
        void given_valid_customer_then_no_exception() {
            var customer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).email("john@example.com").build();
            CustomerValidationUtil.validateForUpdate(customer);
        }

        @Test
        void given_customer_with_null_id_then_throw_exception() {
            var customer = Customer.builder().firstName(FIRST_NAME).lastName(LAST_NAME).email("john@example.com").build();

            assertThatThrownBy(() -> CustomerValidationUtil.validateForUpdate(customer))
                    .isInstanceOf(BusinessViolationException.class)
                    .hasMessage("ID is required for updating a customer");
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", " ", "   "})
        void given_customer_with_blank_or_null_first_name_then_throw_exception(String blankName) {
            var customer = Customer.builder().id(ID).firstName(blankName).lastName(LAST_NAME).email("john@example.com").build();

            assertThatThrownBy(() -> CustomerValidationUtil.validateForUpdate(customer))
                    .isInstanceOf(BusinessViolationException.class)
                    .hasMessage("First name is required");
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", " ", "   "})
        void given_customer_with_blank_or_null_last_name_then_throw_exception(String blankName) {
            var customer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(blankName).email("john@example.com").build();

            assertThatThrownBy(() -> CustomerValidationUtil.validateForUpdate(customer))
                    .isInstanceOf(BusinessViolationException.class)
                    .hasMessage("Last name is required");
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", " ", "   "})
        void given_customer_with_blank_or_null_email_then_throw_exception(String blankEmail) {
            var customer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).email(blankEmail).build();

            assertThatThrownBy(() -> CustomerValidationUtil.validateForUpdate(customer))
                    .isInstanceOf(BusinessViolationException.class)
                    .hasMessage("Invalid email format");
        }

        @ParameterizedTest
        @ValueSource(strings = {"invalid-email", "john@.com", "john@com", "@example.com", "john@example"})
        void given_customer_with_invalid_email_then_throw_exception(String invalidEmail) {
            var customer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).email(invalidEmail).build();

            assertThatThrownBy(() -> CustomerValidationUtil.validateForUpdate(customer))
                    .isInstanceOf(BusinessViolationException.class)
                    .hasMessage("Invalid email format");
        }

        @ParameterizedTest
        @ValueSource(strings = {"john.doe@example.com", "user123@mail.com", "valid_email@sub.example.co.uk"})
        void given_customer_with_valid_email_then_no_exception(String validEmail) {
            var customer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).email(validEmail).build();
            CustomerValidationUtil.validateForUpdate(customer);
        }
    }
}
