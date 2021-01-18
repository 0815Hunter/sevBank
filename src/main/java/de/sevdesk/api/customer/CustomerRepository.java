package de.sevdesk.api.customer;


import de.sevdesk.api.customer.data.entity.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer>, PanacheRepositoryBase<Customer, Long> {
}
