package de.sevdesk.api.account.data.mapper;

import de.sevdesk.api.account.data.entity.Account;
import de.sevdesk.api.account.data.entity.CheckAccount;
import de.sevdesk.api.account.data.entity.TermAccount;
import de.sevdesk.api.account.data.rest.get.AccountGet;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@ApplicationScoped
public class AccountMapper {

    private final CheckAccountMapper checkAccountMapper;
    private final TermAccountMapper termAccountMapper;

    public AccountMapper(CheckAccountMapper checkAccountMapper, TermAccountMapper termAccountMapper) {
        this.checkAccountMapper = checkAccountMapper;
        this.termAccountMapper = termAccountMapper;
    }

    public AccountGet toAccountGet(Account account) {
        AccountGet accountGet;
        if (account instanceof CheckAccount) {
            accountGet = checkAccountMapper.toCheckAccountGet((CheckAccount) account);
        } else {
            accountGet = termAccountMapper.toTermAccountGet((TermAccount) account);
        }
        return accountGet;
    }

    public Set<AccountGet> toAccountGetSet(Set<Account> accounts) {
        return accounts.stream().map(this::toAccountGet).collect(toSet());
    }
}
