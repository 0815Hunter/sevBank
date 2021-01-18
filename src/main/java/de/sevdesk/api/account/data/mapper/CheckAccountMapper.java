package de.sevdesk.api.account.data.mapper;

import de.sevdesk.api.account.data.entity.CheckAccount;
import de.sevdesk.api.account.data.rest.get.CheckAccountGet;
import de.sevdesk.api.account.data.rest.post.CheckAccountPost;
import de.sevdesk.config.mapstruct.QuarkusMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = QuarkusMapperConfig.class)
public interface CheckAccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "customerIds", ignore = true)
    @Mapping(target = "associatedCustomers", ignore = true)
    CheckAccount toCheckAccount(CheckAccountPost checkAccountPost);

    CheckAccountGet toCheckAccountGet(CheckAccount checkAccount);
}
