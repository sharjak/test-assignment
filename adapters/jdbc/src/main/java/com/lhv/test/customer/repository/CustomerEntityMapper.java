package com.lhv.test.customer.repository;

import com.lhv.test.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CustomerEntityMapper {

    Customer toDomain(CustomerEntity entity);

    CustomerEntity toEntity(Customer domain);
}