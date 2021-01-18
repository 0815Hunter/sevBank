package de.sevdesk.api.account.resource;

import de.sevdesk.api.account.data.entity.Account;
import de.sevdesk.api.account.data.rest.get.AccountGet;
import de.sevdesk.api.account.service.AccountService;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/accounts")
public class AccountResource implements PanacheRepository<Account> {

    private final AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<AccountGet> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @POST
    @Path("/{id}/associated-customers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response associateCustomer(@PathParam("id") Long id, @QueryParam("pin") String pin, Long customerId) {

        accountService.associateCustomer(id, pin, customerId);

        return Response.ok().build();
    }

    @POST
    @Path("/{id}/associated-customers/{customerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response disassociateCustomer(@PathParam("id") Long id, @QueryParam("pin") String pin, @PathParam("customerId") Long customerId) {

        accountService.disassociateCustomer(id, pin, customerId);

        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}/name")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateName(@PathParam("id") Long id, @QueryParam("pin") String pin, String name) {

        accountService.updateNameAuthorized(id, pin, name);

        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}/pin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updatePin(@PathParam("id") Long id, @QueryParam("pin") String oldPin, String newPin) {

        accountService.updatePinAuthorized(id, oldPin, newPin);

        return Response.ok().build();
    }

}
