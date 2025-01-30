package com.lhv.test.customer.controller;

import java.util.UUID;

record CustomerDto(UUID id, String firstName, String lastName, String email) {}
