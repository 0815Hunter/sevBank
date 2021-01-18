package de.sevdesk.api.account.data.rest.post;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class CheckAccountPost extends AccountPost {

    private BigDecimal overdraftFacility;

}
