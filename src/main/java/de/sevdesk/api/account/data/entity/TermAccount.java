package de.sevdesk.api.account.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class TermAccount extends Account {

    @Column(precision = 5, scale = 3)
    BigDecimal interestRate = BigDecimal.ZERO;

    LocalDate withdrawalBlockedUntilDate = LocalDate.EPOCH;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
