package de.sevdesk.api.customer;

import de.sevdesk.api.account.data.rest.get.AccountGet;
import de.sevdesk.api.customer.data.rest.CustomerGet;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class CustomerResourceITest {


    @Test
    @TestTransaction
    public void getCustomerTest() {
        Response response = given()
                .get("/customers/1");

        CustomerGet customer = response.getBody().as(CustomerGet.class);

        Set<AccountGet> accounts = customer.getAccounts();

        Assertions.assertNotNull(accounts);

        Assertions.assertEquals(2, accounts.size());
        boolean containsAccountsWithIdOneAndTwo = accounts
                .stream()
                .allMatch(accountGet -> accountGet.getId() == 1 || accountGet.getId() == 2);
        Assertions.assertTrue(containsAccountsWithIdOneAndTwo);

    }
}
