package de.sevdesk.api.account.service;

import de.sevdesk.api.account.AccountRepository;
import de.sevdesk.api.account.data.entity.Account;
import de.sevdesk.api.account.data.rest.get.AccountGet;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import java.util.Set;

@QuarkusTest
public class AccountServiceITest {

    @Inject
    AccountService accountService;

    @Inject
    AccountRepository accountRepository;

    final long accountId = 1L;

    @Test
    @TestTransaction
    public void testGetAllAccounts() {
        Set<AccountGet> allAccounts = accountService.getAllAccounts();

        Assertions.assertNotNull(allAccounts);
    }

    @Test
    @TestTransaction
    public void testUpdateName() {

        Account account = accountRepository.findById(accountId);

        String pin = account.getPin();
        String newName = "newName";

        accountService.updateNameAuthorized(accountId, pin, newName);

        Account accountWithChangedName = accountRepository.findById(accountId);

        Assertions.assertEquals(newName, accountWithChangedName.getName());

    }

    @Test
    @TestTransaction
    public void testUpdatePin() {

        Account account = accountRepository.findById(accountId);

        String oldPin = account.getPin();
        String newPin = "5678";

        accountService.updatePinAuthorized(accountId, oldPin, newPin);

        Assertions.assertEquals(newPin, account.getPin());

    }


    @Test
    @TestTransaction
    public void testGetAccountUnauthorizedFails() {

        Executable executable = () -> {
            String wrongPin = "1234";
            accountService.getAccountAuthorized(accountId, wrongPin);
        };
        Assertions.assertThrows(NotAuthorizedException.class, executable);
    }


}
