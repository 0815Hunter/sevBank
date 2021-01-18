package de.sevdesk.api.customer.data.rest;

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
    private Set<Long> accountIds;

}
