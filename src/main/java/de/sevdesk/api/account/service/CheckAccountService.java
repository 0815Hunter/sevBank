package de.sevdesk.api.account.service;

import de.sevdesk.api.account.data.entity.Account;
import de.sevdesk.api.account.data.entity.CheckAccount;
import de.sevdesk.api.account.data.mapper.CheckAccountMapper;
import de.sevdesk.api.account.data.rest.get.CheckAccountGet;
import de.sevdesk.api.account.data.rest.post.CheckAccountPost;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;

import static de.sevdesk.api.account.service.util.MathUtils.isLowerThan;
import static de.sevdesk.api.account.service.util.MathUtils.isNegative;

@ApplicationScoped
public class CheckAccountService {

    private final AccountService accountService;
    private final CheckAccountMapper checkAccountMapper;

    @ConfigProperty(name = "account.check-account.messages.not-a-check-account-message", defaultValue = "Not a check account!")
    String notACheckAccountMessage;

    public CheckAccountService(AccountService accountService, CheckAccountMapper checkAccountMapper) {
        this.accountService = accountService;
        this.checkAccountMapper = checkAccountMapper;
    }

    public CheckAccount getCheckAccount(Long id) {
        Account account = accountService.getAccount(id);

        if (!(account instanceof CheckAccount)) {
            throw new BadRequestException(notACheckAccountMessage);
        }

        return (CheckAccount) account;
    }

    public CheckAccountGet getCheckAccountGet(Long id) {
        CheckAccount checkAccount = getCheckAccount(id);

        return checkAccountMapper.toCheckAccountGet(checkAccount);
    }

    public CheckAccount getCheckAccountAuthorized(Long id, String pin) {
        Account account = accountService.getAccountAuthorized(id, pin);

        if (!(account instanceof CheckAccount)) {
            throw new BadRequestException(notACheckAccountMessage);
        }

        return (CheckAccount) account;
    }

    public Long addCheckAccount(CheckAccountPost checkAccountPost) {

        CheckAccount checkAccount = checkAccountMapper.toCheckAccount(checkAccountPost);

        return accountService.addAccount(checkAccount);
    }

    public void updateOverdraftFacilityAuthorized(Long id, String pin, BigDecimal newOverdraftFacility) {

        CheckAccount account = getCheckAccountAuthorized(id, pin);

        account.setOverdraftFacility(newOverdraftFacility);

        accountService.updateAccount(account);

    }

    public void updateBalanceAuthorized(Long id, String pin, BigDecimal amount) {
        CheckAccount checkAccount = getCheckAccountAuthorized(id, pin);

        BigDecimal oldBalance = checkAccount.getBalance();

        BigDecimal balanceWithAmount = oldBalance.add(amount);

        if (isNegative(balanceWithAmount)) {
            BigDecimal overdraftFacility = checkAccount.getOverdraftFacilityNegative();

            if (isLowerThan(balanceWithAmount, overdraftFacility))
                throw new BadRequestException("Balance too low for requested transaction.");
        }

        checkAccount.setBalance(balanceWithAmount);

        accountService.updateAccount(checkAccount);

    }
}
