package de.sevdesk.api.account.data.rest.get;

import de.sevdesk.api.account.data.rest.get.config.AccountGetJsonConfig;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AccountGetJsonConfig
public class AccountGet {

    private Long id;
    private String name;
    private BigDecimal balance;
    private Set<Long> customerIds;

}
