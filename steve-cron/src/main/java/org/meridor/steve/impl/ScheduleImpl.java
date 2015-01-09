package org.meridor.steve.impl;

import org.meridor.steve.Schedule;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleImpl implements Schedule {

    private final List<Integer> seconds;
    private final List<Integer> minutes;
    private final List<Integer> hours;
    private final List<Integer> days;
    private final List<Integer> months;
    private final List<Integer> years;

    public ScheduleImpl(List<Integer> seconds, List<Integer> minutes, List<Integer> hours, List<Integer> days, List<Integer> months, List<Integer> years) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
        this.days = days;
        this.months = months;
        this.years = years;
    }

    @Override
    public List<Integer> getSeconds() {
        return seconds;
    }

    @Override
    public List<Integer> getMinutes() {
        return minutes;
    }

    @Override
    public List<Integer> getHours() {
        return hours;
    }

    @Override
    public List<MonthDay> getDays() {
        ArrayList<MonthDay> ret = new ArrayList<>();
        if (days == null || days.isEmpty()) {
            return Collections.emptyList();
        }
        List<Month> monthsToIterate = getMonths().isEmpty() ?
                Arrays.asList(Month.values()) : getMonths();
        for (Month month : monthsToIterate) {
            ret.addAll(
                    days.stream()
                            .map(day -> MonthDay.of(month, day))
                            .collect(Collectors.toList())
            );
        }
        return ret;
    }

    @Override
    public List<Month> getMonths() {
        return months.stream()
                .map(Month::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<Year> getYears() {
        return years.stream()
                .map(Year::of)
                .collect(Collectors.toList());
    }

}
