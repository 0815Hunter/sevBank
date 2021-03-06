package de.sevdesk.api.customer.data.mapper;

import de.sevdesk.api.account.data.mapper.AccountMapper;
import de.sevdesk.api.customer.data.entity.Customer;
import de.sevdesk.api.customer.data.rest.CustomerGet;
import de.sevdesk.config.mapstruct.QuarkusMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = QuarkusMapperConfig.class, uses = AccountMapper.class)
public interface CustomerMapper {


    CustomerGet toCustomerGet(Customer customer);

}
