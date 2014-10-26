package ru.meridor.steve.impl;

import org.junit.Before;
import org.junit.Test;
import ru.meridor.steve.Schedule;

import java.time.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ScheduleImplTest {

    private final List<Integer> inputSeconds = Arrays.asList(20);
    private final List<Integer> inputMinutes = Arrays.asList(10);
    private final List<Integer> inputHours = Arrays.asList(5);
    private final List<Integer> inputDays = Arrays.asList(5);
    private final List<Integer> inputMonths = Arrays.asList(3, 6);
    private final List<Integer> inputYears = Arrays.asList(2014);

    @Test
    public void testAPI() {

        Schedule schedule = new ScheduleImpl(
                inputSeconds,
                inputMinutes,
                inputHours,
                inputDays,
                inputMonths,
                inputYears
        );

        assertThat(schedule.getSeconds(), equalTo(inputSeconds));
        assertThat(schedule.getMinutes(), equalTo(inputMinutes));
        assertThat(schedule.getHours(), equalTo(inputHours));
        assertThat(schedule.getDays(), equalTo(Arrays.asList(
                MonthDay.of(3, 5),
                MonthDay.of(6, 5)
        )));
        assertThat(schedule.getMonths(), equalTo(Arrays.asList(Month.of(3), Month.of(6))));
        assertThat(schedule.getYears(), equalTo(Arrays.asList(Year.of(2014))));
    }

    @Test
    public void testEmptyMonths() {
        Schedule schedule = new ScheduleImpl(
                inputSeconds,
                inputMinutes,
                inputHours,
                inputDays,
                Collections.emptyList(),
                inputYears
        );

        assertThat(schedule.getDays(), equalTo(Arrays.asList(
                MonthDay.of(Month.JANUARY, 5),
                MonthDay.of(Month.FEBRUARY, 5),
                MonthDay.of(Month.MARCH, 5),
                MonthDay.of(Month.APRIL, 5),
                MonthDay.of(Month.MAY, 5),
                MonthDay.of(Month.JUNE, 5),
                MonthDay.of(Month.JULY, 5),
                MonthDay.of(Month.AUGUST, 5),
                MonthDay.of(Month.SEPTEMBER, 5),
                MonthDay.of(Month.OCTOBER, 5),
                MonthDay.of(Month.NOVEMBER, 5),
                MonthDay.of(Month.DECEMBER, 5)
        )));
    }

}
