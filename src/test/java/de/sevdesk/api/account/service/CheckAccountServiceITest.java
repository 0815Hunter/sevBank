package de.sevdesk.api.account.service;

import de.sevdesk.api.account.AccountRepository;
import de.sevdesk.api.account.data.entity.Account;
import de.sevdesk.api.account.data.entity.CheckAccount;
import de.sevdesk.api.account.service.CheckAccountService;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class CheckAccountServiceITest {

    @Inject
    CheckAccountService checkAccountService;

    @Inject
    AccountRepository accountRepository;

    final long checkAccountId = 1L;

    @Test
    @TestTransaction
    public void testUpdateBalanceConstraints() {
        Account account = accountRepository.findById(checkAccountId);
        ((CheckAccount) account).setOverdraftFacility(BigDecimal.ONE);
        accountRepository.persist(account);

        assertDoesNotThrow(() -> checkAccountService.updateBalanceAuthorized(checkAccountId, "1337", BigDecimal.valueOf(-1)));
        assertThrows(BadRequestException.class, () -> checkAccountService.updateBalanceAuthorized(checkAccountId, "1337", new BigDecimal("-1.01")));

    }


}
