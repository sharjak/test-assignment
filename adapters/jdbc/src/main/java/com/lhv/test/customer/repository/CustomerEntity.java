package com.lhv.test.customer.repository;

import com.lhv.test.common.BaseAuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "customer")
@Getter
@Setter
class CustomerEntity extends BaseAuditableEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;
}
