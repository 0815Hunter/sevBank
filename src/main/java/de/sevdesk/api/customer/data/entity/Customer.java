package de.sevdesk.api.customer.data.entity;


import de.sevdesk.api.account.data.entity.Account;
import de.sevdesk.api.account.data.entity.CheckAccount;
import de.sevdesk.api.account.data.entity.TermAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

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

    @Transient
    Set<TermAccount> termAccounts;
    @Transient
    Set<CheckAccount> checkAccounts;

    public Set<TermAccount> getTermAccounts() {
        if (termAccounts == null) {
            termAccounts = getAccounts()
                    .stream()
                    .filter(account -> account instanceof TermAccount)
                    .map(account -> (TermAccount) account).collect(toSet());
        }
        return termAccounts;
    }

    public Set<CheckAccount> getCheckAccounts() {
        if (checkAccounts == null) {
            checkAccounts = getAccounts()
                    .stream()
                    .filter(account -> account instanceof CheckAccount)
                    .map(account -> (CheckAccount) account).collect(toSet());
        }
        return checkAccounts;
    }


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
