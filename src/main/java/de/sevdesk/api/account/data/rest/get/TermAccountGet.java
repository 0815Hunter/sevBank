package de.sevdesk.api.account.data.rest.get;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class TermAccountGet extends AccountGet {

    BigDecimal interestRate;
    LocalDate withdrawalBlockedUntilDate;

}
