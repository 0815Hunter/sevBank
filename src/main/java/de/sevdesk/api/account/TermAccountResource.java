package de.sevdesk.api.account;

import de.sevdesk.api.account.data.rest.get.TermAccountGet;
import de.sevdesk.api.account.data.rest.post.TermAccountPost;
import de.sevdesk.api.account.service.TermAccountService;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/accounts/term-accounts")
public class TermAccountResource {

    private final TermAccountService termAccountService;

    public TermAccountResource(TermAccountService termAccountService) {
        this.termAccountService = termAccountService;
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public TermAccountGet getTermAccount(@PathParam("id") Long id) {
        return termAccountService.getTermAccountGet(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addTermAccount(TermAccountPost termAccountPost) {

        Long accountId = termAccountService.addTermAccount(termAccountPost);

        return Response.ok().entity(accountId).build();
    }

    @PATCH
    @Path("/{id}/interest-rate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateInterestRate(@PathParam("id") Long id, @QueryParam("pin") String pin, BigDecimal interestRate) {

        termAccountService.updateInterestRateAuthorized(id, pin, interestRate);

        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}/balance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateBalance(@PathParam("id") Long id, @QueryParam("pin") String pin, BigDecimal amount) {

        termAccountService.updateBalanceAuthorized(id, pin, amount);

        return Response.ok().build();
    }
}
