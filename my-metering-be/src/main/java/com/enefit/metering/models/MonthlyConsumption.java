package com.enefit.metering.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface MonthlyConsumption {
//    OffsetDateTime getMonth();
    java.sql.Timestamp getMonth();
    BigDecimal getTotalConsumption();
}
