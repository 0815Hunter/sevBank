package de.sevdesk.api.account.service;

import de.sevdesk.api.account.AccountRepository;
import de.sevdesk.api.account.data.entity.TermAccount;
import de.sevdesk.api.account.service.TermAccountService;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class TermAccountServiceITest {

    @Inject
    TermAccountService termAccountService;

    @Inject
    AccountRepository accountRepository;

    final long termAccountId = 3L;

    @Test
    @TestTransaction
    public void testNoNegativeButPositiveBalance() {
        TermAccount termAccount = (TermAccount) accountRepository.findById(termAccountId);

        termAccount.setWithdrawalBlockedUntilDate(LocalDate.now());
        termAccount.setBalance(BigDecimal.TEN);
        accountRepository.persist(termAccount);

        assertThrows(BadRequestException.class, () -> termAccountService.updateBalanceAuthorized(termAccountId, "1337", new BigDecimal("-10.01")));

        assertDoesNotThrow(() -> termAccountService.updateBalanceAuthorized(termAccountId, "1337", new BigDecimal("0.01")));

    }

    @Test
    @TestTransaction
    public void testWithdrawBefore_WithdrawalBlockedUntilDate_AndFail() {
        TermAccount termAccount = (TermAccount) accountRepository.findById(termAccountId);

        termAccount.setWithdrawalBlockedUntilDate(LocalDate.now());
        termAccount.setBalance(BigDecimal.TEN);
        accountRepository.persist(termAccount);

        assertThrows(BadRequestException.class, () -> termAccountService.updateBalanceAuthorized(termAccountId, "1337", new BigDecimal(-1)));
    }

    @Test
    @TestTransaction
    public void testWithdrawOn_WithdrawalBlockedUntilDate_AndFail() {
        TermAccount termAccount = (TermAccount) accountRepository.findById(termAccountId);

        termAccount.setWithdrawalBlockedUntilDate(LocalDate.now());
        termAccount.setBalance(BigDecimal.TEN);
        accountRepository.persist(termAccount);

        assertThrows(BadRequestException.class, () -> termAccountService.updateBalanceAuthorized(termAccountId, "1337", new BigDecimal(-1)));
    }

    @Test
    @TestTransaction
    public void testWithDrawAfter_WithdrawalBlockedUntilDate_AndSucceed() {

        termAccountService.updateBalanceAuthorized(termAccountId, "1337", new BigDecimal("100"));

        TermAccount termAccount = (TermAccount) accountRepository.findById(termAccountId);
        termAccount.setWithdrawalBlockedUntilDate(LocalDate.now().minusDays(1));
        accountRepository.persist(termAccount);

        assertDoesNotThrow(() -> termAccountService.updateBalanceAuthorized(termAccountId, "1337", new BigDecimal(-1)));
    }
}
