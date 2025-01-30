package com.lhv.test.customer.controller;

import com.lhv.test.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
interface CustomerDtoMapper {
    CustomerDto toDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    Customer toDomain(SaveCustomerDto saveCustomerDto);

    Customer toDomain(UUID id, SaveCustomerDto saveCustomerDto);
}
