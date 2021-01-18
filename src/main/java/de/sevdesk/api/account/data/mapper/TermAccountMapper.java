package de.sevdesk.api.account.data.mapper;

import de.sevdesk.api.account.data.entity.TermAccount;
import de.sevdesk.api.account.data.rest.get.TermAccountGet;
import de.sevdesk.api.account.data.rest.post.TermAccountPost;
import de.sevdesk.config.mapstruct.QuarkusMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = QuarkusMapperConfig.class)
public interface TermAccountMapper {

    @Mapping(target = "customerIds", ignore = true)
    @Mapping(target = "withdrawalBlockedUntilDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "associatedCustomers", ignore = true)
    TermAccount toTermAccount(TermAccountPost termAccountPost);

    TermAccountGet toTermAccountGet(TermAccount termAccount);

}