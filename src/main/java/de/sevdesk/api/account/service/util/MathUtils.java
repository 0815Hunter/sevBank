package de.sevdesk.api.account.service.util;

import java.math.BigDecimal;

public class MathUtils {

    public static boolean isNegative(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean isLowerThan(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0;
    }
}
