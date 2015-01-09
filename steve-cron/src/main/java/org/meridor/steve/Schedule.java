package org.meridor.steve;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;

/**
 * Cron-based schedule in format * * * * * where:
 * <b>{@link java.util.Optional#empty()}</b> value means any value (* in cron specification)
 * <b>List<Integer></b> means that there can be several values present
 */
public interface Schedule {

    List<Integer> getSeconds();

    List<Integer> getMinutes();

    List<Integer> getHours();

    List<MonthDay> getDays();

    List<Month> getMonths();

    List<Year> getYears();

}
