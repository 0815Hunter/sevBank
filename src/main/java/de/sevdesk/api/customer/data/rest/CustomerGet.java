package de.sevdesk.api.customer.data.rest;

import de.sevdesk.api.account.data.rest.get.CheckAccountGet;
import de.sevdesk.api.account.data.rest.get.TermAccountGet;
import de.sevdesk.api.customer.data.entity.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class CustomerGet {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private Set<CheckAccountGet> checkAccounts;
    private Set<TermAccountGet> termAccounts;

}
