package de.sevdesk.api.customer.data.entity;


import de.sevdesk.api.account.data.entity.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence", allocationSize = 1)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;

    @ElementCollection
    @CollectionTable(name = "customer_to_account", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "account_id")
    private Set<Long> accountIds = new HashSet<>();

    @ManyToMany(mappedBy = "associatedCustomers")
    private Set<Account> accounts = new HashSet<>();

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
