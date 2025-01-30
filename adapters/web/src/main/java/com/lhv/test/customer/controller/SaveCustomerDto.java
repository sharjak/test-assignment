package com.lhv.test.customer.controller;

import jakarta.validation.constraints.NotNull;

record SaveCustomerDto (@NotNull String firstName,
                        @NotNull String lastName,
                        @NotNull String email) {}
