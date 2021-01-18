package de.sevdesk.api.customer;

import de.sevdesk.api.customer.data.entity.Customer;
import de.sevdesk.api.customer.data.mapper.CustomerMapper;
import de.sevdesk.api.customer.data.rest.CustomerGet;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@ApplicationScoped
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public Customer getCustomer(Long id) {
        Optional<Customer> maybeCustomer = customerRepository.findByIdOptional(id);

        if (maybeCustomer.isEmpty()) {
            throw new BadRequestException("Customer with id " + id + " does not exist");
        }

        return maybeCustomer.get();
    }

    public CustomerGet getCustomerAsCustomerGet(Long id) {
        Customer customer = getCustomer(id);

        return customerMapper.toCustomerGet(customer);
    }

    public Set<CustomerGet> getAllCustomerAsCustomerGetSet() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::toCustomerGet)
                .collect(toSet());
    }

    public void addCustomer(Customer customer) {
        customerRepository.persist(customer);
    }

    public void updateCustomer(Long id, Customer customer) {
        Optional<Customer> maybePersistedCustomer = customerRepository.findByIdOptional(id);

        if (maybePersistedCustomer.isEmpty()) {
            throw new BadRequestException("Customer with id " + id + " does not exist.");
        }
        Customer persistedCustomer = maybePersistedCustomer.get();

        persistedCustomer.setBirthDate(customer.getBirthDate());
        persistedCustomer.setFirstName(customer.getFirstName());
        persistedCustomer.setLastName(customer.getLastName());
        persistedCustomer.setGender(customer.getGender());

        customerRepository.persist(persistedCustomer);
    }
}
