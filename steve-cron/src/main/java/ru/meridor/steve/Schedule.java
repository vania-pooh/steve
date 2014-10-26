package ru.meridor.steve;

import java.time.*;
import java.util.List;
import java.util.Optional;

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
