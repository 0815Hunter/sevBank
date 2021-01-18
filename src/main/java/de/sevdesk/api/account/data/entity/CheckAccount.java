package de.sevdesk.api.account.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class CheckAccount extends Account {

    @Column(precision = 19, scale = 2)
    private BigDecimal overdraftFacility = BigDecimal.ZERO;

    public BigDecimal getOverdraftFacilityNegative() {
        return overdraftFacility.negate();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
