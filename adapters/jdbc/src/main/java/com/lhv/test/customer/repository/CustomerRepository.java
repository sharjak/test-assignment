package com.lhv.test.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {}
