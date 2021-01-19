package de.sevdesk.api.account.data.mapper;

import de.sevdesk.api.account.data.entity.Account;
import de.sevdesk.api.account.data.entity.CheckAccount;
import de.sevdesk.api.account.data.entity.TermAccount;
import de.sevdesk.api.account.data.rest.get.AccountGet;
import de.sevdesk.config.mapstruct.QuarkusMapperConfig;
import org.mapstruct.Mapper;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Mapper(config = QuarkusMapperConfig.class)
public abstract class AccountMapper {

    @Inject
    CheckAccountMapper checkAccountMapper;
    @Inject
    TermAccountMapper termAccountMapper;

    protected abstract AccountGet _toAccountGet(Account account);

    public AccountGet toAccountGet(Account account) {
        AccountGet accountGet;
        if (account instanceof CheckAccount) {
            accountGet = checkAccountMapper.toCheckAccountGet((CheckAccount) account);
        } else if (account instanceof TermAccount) {
            accountGet = termAccountMapper.toTermAccountGet((TermAccount) account);
        } else {
            accountGet = _toAccountGet(account);
        }
        return accountGet;
    }

    public Set<AccountGet> toAccountGetSet(Set<Account> accounts) {
        return accounts.stream().map(this::toAccountGet).collect(toSet());
    }
}
