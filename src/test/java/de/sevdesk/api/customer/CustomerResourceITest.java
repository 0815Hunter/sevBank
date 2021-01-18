package de.sevdesk.api.customer;

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

        Set<Long> accountIds = customer.getAccountIds();

        Assertions.assertNotNull(accountIds);

        Assertions.assertEquals(2, accountIds.size());

    }
}
