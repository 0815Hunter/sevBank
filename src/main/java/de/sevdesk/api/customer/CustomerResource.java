package de.sevdesk.api.customer;

import de.sevdesk.api.customer.data.entity.Customer;
import de.sevdesk.api.customer.data.rest.CustomerGet;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/customers")
public class CustomerResource {

    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Set<CustomerGet> getCustomers() {

        return customerService.getAllCustomerAsCustomerGetSet();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public CustomerGet getCustomer(@PathParam("id") Long id) {

        return customerService.getCustomerAsCustomerGet(id);
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Long postCustomer(Customer customer) {
        customerService.addCustomer(customer);
        return customer.getId();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response putCustomer(@PathParam("id") Long id, Customer customer) {
        customerService.updateCustomer(id, customer);
        return Response.ok().build();
    }

}
