package org.meridor.steve.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.meridor.steve.Schedule;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CronParserImplCorrectValuesTest {

    private final String cronString;

    private final List<Integer> seconds;

    private final List<Integer> minutes;

    private final List<Integer> hours;

    private final List<MonthDay> days;

    private final List<Month> months;

    private final List<Year> years;

    @Parameters(name = "{0}")
    public static Iterable<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
                //Any values
                {"* * * * *", null, null, null, null, null, null},
                {"* * * * * *", null, null, null, null, null, null},

                //Seconds
                {"2 * * * * *", Arrays.asList(2), null, null, null, null, null},
                {"1-3 * * * * *", Arrays.asList(1, 2, 3), null, null, null, null, null},
                {"30,58 * * * * *", Arrays.asList(30, 58), null, null, null, null, null},
                {"45/5 * * * * *", Arrays.asList(45, 50, 55), null, null, null, null, null},
                {"*/25 * * * * *", Arrays.asList(0, 25, 50), null, null, null, null, null},

                //Minutes
                {"* 22 * * * *", null, Arrays.asList(22), null, null, null, null},
                {"* 57-59 * * * *", null, Arrays.asList(57, 58, 59), null, null, null, null},
                {"* 21,43,51 * * * *", null, Arrays.asList(21, 43, 51), null, null, null, null},
                {"* 10/10 * * * *", null, Arrays.asList(10, 20, 30, 40, 50), null, null, null, null},
                {"* */15 * * * *", null, Arrays.asList(0, 15, 30, 45), null, null, null, null},

                //Hours
                {"* * 3 * * *", null, null, Arrays.asList(3), null, null, null},
                {"* * 2-7 * * *", null, null, Arrays.asList(2, 3, 4, 5, 6, 7), null, null, null},
                {"* * 1,3,5 * * *", null, null, Arrays.asList(1, 3, 5), null, null, null},
                {"* * 2/4 * * *", null, null, Arrays.asList(2, 6, 10, 14, 18, 22), null, null, null},
                {"* * */5 * * *", null, null, Arrays.asList(0, 5, 10, 15, 20), null, null, null},

                //Days
                {"* * * 21 1 *", null, null, null, Arrays.asList(MonthDay.of(1, 21)), Arrays.asList(Month.JANUARY), null},
                {"* * * 1-2 2,3 *", null, null, null, Arrays.asList(MonthDay.of(2, 1), MonthDay.of(2, 2), MonthDay.of(3, 1), MonthDay.of(3, 2)), Arrays.asList(Month.FEBRUARY, Month.MARCH), null},
                {"* * * 3,20 4 *", null, null, null, Arrays.asList(MonthDay.of(4, 3), MonthDay.of(4, 20)), Arrays.asList(Month.APRIL), null},
                {"* * * 2/10 5 *", null, null, null, Arrays.asList(MonthDay.of(5, 2), MonthDay.of(5, 12), MonthDay.of(5, 22)), Arrays.asList(Month.MAY), null},
                {"* * * */16 7 *", null, null, null, Arrays.asList(MonthDay.of(7, 1), MonthDay.of(7, 17)), Arrays.asList(Month.JULY), null},

                //Months
                {"* * * * 3 *", null, null, null, null, Arrays.asList(Month.MARCH), null},
                {"* * * * 10-12 *", null, null, null, null, Arrays.asList(Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER), null},
                {"* * * * 3,4,5 *", null, null, null, null, Arrays.asList(Month.MARCH, Month.APRIL, Month.MAY), null},
                {"* * * * 2/4 *", null, null, null, null, Arrays.asList(Month.FEBRUARY, Month.JUNE, Month.OCTOBER), null},
                {"* * * * */5 *", null, null, null, null, Arrays.asList(Month.JANUARY, Month.JUNE, Month.NOVEMBER), null},

                //Years (increment is not supported)
                {"* * * * * 2014", null, null, null, null, null, Arrays.asList(Year.of(2014))},
                {"* * * * * 2014-2016", null, null, null, null, null, Arrays.asList(Year.of(2014), Year.of(2015), Year.of(2016))},
                {"* * * * * 2012,2015,2009", null, null, null, null, null, Arrays.asList(Year.of(2012), Year.of(2015), Year.of(2009))},

                //Combinations
                {"1 1 1 1 1 2014", Arrays.asList(1), Arrays.asList(1), Arrays.asList(1), Arrays.asList(MonthDay.of(1, 1)), Arrays.asList(Month.JANUARY), Arrays.asList(Year.of(2014))},

        });
    }

    public CronParserImplCorrectValuesTest(String cronString, List<Integer> seconds, List<Integer> minutes, List<Integer> hours, List<MonthDay> days, List<Month> months, List<Year> years) {
        this.cronString = cronString;
        this.seconds = (seconds != null) ? seconds : Collections.emptyList();
        this.minutes = (minutes != null) ? minutes : Collections.emptyList();
        this.hours = (hours != null) ? hours : Collections.emptyList();
        this.days = (days != null) ? days : Collections.emptyList();
        this.months = (months != null) ? months : Collections.emptyList();
        this.years = (years != null) ? years : Collections.emptyList();
    }

    @Test
    public void testIsParsedCorrectly() throws Exception {
        Schedule schedule = new CronParserImpl().parse(cronString);
        assertThat(schedule.getSeconds(), equalTo(seconds));
        assertThat(schedule.getMinutes(), equalTo(minutes));
        assertThat(schedule.getHours(), equalTo(hours));
        assertThat(schedule.getDays(), equalTo(days));
        assertThat(schedule.getMonths(), equalTo(months));
        assertThat(schedule.getYears(), equalTo(years));
    }

}
