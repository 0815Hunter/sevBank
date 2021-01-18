package de.sevdesk.api.account.data.entity;

import de.sevdesk.api.customer.data.entity.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Inheritance
@Table
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence", allocationSize = 1, initialValue = 5)
    private Long id;

    private String name;

    @Column(precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    private String pin;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "customer_to_account",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<Customer> associatedCustomers = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "customer_to_account", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "customer_id")
    private Set<Long> customerIds = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Customer))
            return false;

        Customer other = (Customer) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
