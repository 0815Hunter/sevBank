package de.sevdesk.api.customer;

import de.sevdesk.api.account.AccountRepository;
import de.sevdesk.api.account.data.entity.Account;
import de.sevdesk.api.customer.data.entity.Customer;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Set;

@QuarkusTest
public class CustomerRepositoryTest {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    AccountRepository accountRepository;


    @Test
    @TestTransaction
    public void testAssociateUserWithAccounts() {

        long custId = 2L;
        long accId1 = 1L;
        long accId2 = 2L;

        Customer customer = customerRepository.findById(custId);

        Account account1 = accountRepository.findById(accId1);
        Account account2 = accountRepository.findById(accId2);

        account1.getAssociatedCustomers().add(customer);
        account2.getAssociatedCustomers().add(customer);

        accountRepository.persist(account1);
        accountRepository.persist(account2);

        accountRepository.flush(); // needed -> is an update of a relation

        Set<Long> accountIds = customer.getAccountIds();

        Assertions.assertTrue(accountIds.containsAll(Set.of(accId1, accId2)));

    }


}
