package de.sevdesk.api.account.service;

import de.sevdesk.api.account.data.entity.Account;
import de.sevdesk.api.account.data.entity.TermAccount;
import de.sevdesk.api.account.data.mapper.TermAccountMapper;
import de.sevdesk.api.account.data.rest.get.TermAccountGet;
import de.sevdesk.api.account.data.rest.post.TermAccountPost;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static de.sevdesk.api.account.service.util.MathUtils.isNegative;

@ApplicationScoped
public class TermAccountService {

    private final AccountService accountService;
    private final TermAccountMapper termAccountMapper;

    @ConfigProperty(name = "account.check-account.messages.not-a-term-account-message", defaultValue = "Not a term account!")
    String notATermAccountMessage;


    public TermAccountService(AccountService accountService, TermAccountMapper termAccountMapper) {
        this.accountService = accountService;
        this.termAccountMapper = termAccountMapper;
    }

    public TermAccount getTermAccount(Long id) {
        Account account = accountService.getAccount(id);

        if (!(account instanceof TermAccount)) {
            throw new BadRequestException(notATermAccountMessage);
        }

        return (TermAccount) account;
    }

    public TermAccountGet getTermAccountGet(Long id) {
        TermAccount termAccount = getTermAccount(id);

        return termAccountMapper.toTermAccountGet(termAccount);
    }


    public TermAccount getTermAccountAuthorized(Long id, String pin) {
        Account account = accountService.getAccountAuthorized(id, pin);

        if (!(account instanceof TermAccount)) {
            throw new BadRequestException(notATermAccountMessage);
        }

        return (TermAccount) account;
    }

    public Long addTermAccount(TermAccountPost termAccountPost) {

        TermAccount termAccount = termAccountMapper.toTermAccount(termAccountPost);

        return accountService.addAccount(termAccount);
    }


    public void updateInterestRateAuthorized(Long id, String pin, BigDecimal interestRate) {
        TermAccount termAccount = getTermAccountAuthorized(id, pin);

        termAccount.setInterestRate(interestRate);

        accountService.updateAccount(termAccount);
    }

    public void updateBalanceAuthorized(Long id, String pin, BigDecimal amount) {
        TermAccount termAccount = getTermAccountAuthorized(id, pin);

        if (isNegative(amount)) {
            LocalDate withdrawalBlockedUntilDate = termAccount.getWithdrawalBlockedUntilDate();

            if (!LocalDate.now().isAfter(withdrawalBlockedUntilDate)) {
                throw new BadRequestException("Withdrawal blocked until " + withdrawalBlockedUntilDate + ".");
            }
        }

        BigDecimal oldBalance = termAccount.getBalance();

        BigDecimal balanceWithAmount = oldBalance.add(amount);

        if (isNegative(balanceWithAmount)) {
            throw new BadRequestException("Balance too low for requested transaction.");
        }

        termAccount.setBalance(balanceWithAmount);
        termAccount.setWithdrawalBlockedUntilDate(LocalDate.now().plusMonths(1));

        accountService.updateAccount(termAccount);

    }


}
