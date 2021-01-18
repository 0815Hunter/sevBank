package de.sevdesk.api.account;

import de.sevdesk.api.account.data.rest.get.CheckAccountGet;
import de.sevdesk.api.account.data.rest.post.CheckAccountPost;
import de.sevdesk.api.account.service.CheckAccountService;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/accounts/check-accounts")
public class CheckAccountResource {

    private final CheckAccountService checkAccountService;

    public CheckAccountResource(CheckAccountService checkAccountService) {
        this.checkAccountService = checkAccountService;
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public CheckAccountGet getCheckAccount(@PathParam("id") Long id) {
        return checkAccountService.getCheckAccountGet(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postCheckAccount(CheckAccountPost checkAccountPost) {

        Long accountId = checkAccountService.addCheckAccount(checkAccountPost);

        return Response.ok().entity(accountId).build();
    }

    @PATCH
    @Path("/{id}/overdraft-facility")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateOverdraftFacility(@PathParam("id") Long id, @QueryParam("pin") String pin, BigDecimal overdraftFacility) {

        checkAccountService.updateOverdraftFacilityAuthorized(id, pin, overdraftFacility);

        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}/balance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateBalance(@PathParam("id") Long id, @QueryParam("pin") String pin, BigDecimal amount) {

        checkAccountService.updateBalanceAuthorized(id, pin, amount);

        return Response.ok().build();
    }

}
