package de.sevdesk.api.account.data.rest.get;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class CheckAccountGet extends AccountGet {

    private BigDecimal overdraftFacility;

}
