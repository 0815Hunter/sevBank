package de.sevdesk.api.customer.data.mapper;

import de.sevdesk.api.customer.data.entity.Customer;
import de.sevdesk.api.customer.data.rest.CustomerGet;
import de.sevdesk.config.mapstruct.QuarkusMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = QuarkusMapperConfig.class)
public interface CustomerMapper {

    @Mapping(target = "checkAccounts", source = "checkAccounts")
    @Mapping(target = "termAccounts", source = "termAccounts")
    CustomerGet toCustomerGet(Customer customer);

}
