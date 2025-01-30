package com.lhv.test.customer.util;

import com.lhv.test.common.exception.BusinessViolationException;
import com.lhv.test.customer.model.Customer;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class CustomerValidationUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static void validateForCreate(Customer customer) {
        if (customer.getId() != null) {
            throw new BusinessViolationException("ID must be null when creating a customer");
        }
        validateCommonFields(customer);
    }

    public static void validateForUpdate(Customer customer) {
        if (customer.getId() == null) {
            throw new BusinessViolationException("ID is required for updating a customer");
        }
        validateCommonFields(customer);
    }

    private static void validateCommonFields(Customer customer) {
        if (isBlank(customer.getFirstName())) {
            throw new BusinessViolationException("First name is required");
        }
        if (isBlank(customer.getLastName())) {
            throw new BusinessViolationException("Last name is required");
        }
        if (isBlank(customer.getEmail()) || !EMAIL_PATTERN.matcher(customer.getEmail()).matches()) {
            throw new BusinessViolationException("Invalid email format");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
