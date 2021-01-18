package de.sevdesk.api.account.service;

import de.sevdesk.api.account.AccountRepository;
import de.sevdesk.api.account.data.entity.Account;
import de.sevdesk.api.account.data.mapper.AccountMapper;
import de.sevdesk.api.account.data.rest.get.AccountGet;
import de.sevdesk.api.customer.CustomerService;
import de.sevdesk.api.customer.data.entity.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@ApplicationScoped
public class AccountService {


    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountMapper = accountMapper;
    }

    public Account getAccount(Long id) {
        Optional<Account> maybeAccount = accountRepository.findByIdOptional(id);

        if (maybeAccount.isEmpty()) {
            throw new BadRequestException("Account with id " + id + " not available.");
        }
        return maybeAccount.get();
    }

    public Account getAccountAuthorized(Long id, String pin) {
        Account account = getAccount(id);

        if (!account.getPin().equals(pin)) {
            throw new NotAuthorizedException("Wrong Pin.");
        }

        return account;
    }

    public Set<AccountGet> getAllAccounts() {
        Set<Account> accounts = accountRepository.findAll().stream().collect(toSet());
        return accountMapper.toAccountGetSet(accounts);
    }

    public Long addAccount(Account account) {
        account.setBalance(BigDecimal.ZERO);
        accountRepository.persist(account);
        return account.getId();
    }

    public void updateAccount(Account account) {
        accountRepository.persist(account);
    }


    public void associateCustomer(Long id, String pin, Long customerId) {
        Account account = getAccountAuthorized(id, pin);

        Customer customer = customerService.getCustomer(customerId);

        account.getAssociatedCustomers().add(customer);

        updateAccount(account);
    }

    public void disassociateCustomer(Long id, String pin, Long customerId) {
        Account account = getAccountAuthorized(id, pin);

        Customer customer = customerService.getCustomer(customerId);

        account.getAssociatedCustomers().remove(customer);

        updateAccount(account);
    }

    public void updateNameAuthorized(Long id, String pin, String name) {
        Account account = getAccountAuthorized(id, pin);

        account.setName(name);
        updateAccount(account);
    }


    public void updatePinAuthorized(Long id, String oldPin, String newPin) {
        Account account = getAccountAuthorized(id, oldPin);

        account.setPin(newPin);
        updateAccount(account);
    }

}
