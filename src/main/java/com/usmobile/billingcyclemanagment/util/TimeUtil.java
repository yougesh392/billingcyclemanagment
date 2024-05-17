package com.usmobile.billingcyclemanagment.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtil {
    public static Date getCycleStartDate() {
        LocalDate today = LocalDate.now();
        return Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getCycleEndDate() {
        LocalDate dateAfter30Days = LocalDate.now().plus(30, ChronoUnit.DAYS);
        return Date.from(dateAfter30Days.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
