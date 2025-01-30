package com.lhv.test.customer.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class Customer {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}