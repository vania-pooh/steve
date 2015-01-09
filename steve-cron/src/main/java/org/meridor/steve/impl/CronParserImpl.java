package org.meridor.steve.impl;

import org.meridor.steve.CronParser;
import org.meridor.steve.Schedule;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CronParserImpl implements CronParser {

    private static final String ANY = "*";
    private static final String RANGE = "-";
    private static final String ENUMERATION = ",";
    private static final String INCREMENT = "/";


    @Override
    public Schedule parse(CharSequence cronString) throws ParseException {
        if (cronString == null) {
            throw new ParseException("Cron string can't be null", 0);
        }
        List<String> cronPieces = splitToPieces(cronString);

        if (!isNumberOfPiecesCorrect(cronPieces)) {
            throw new ParseException("Cron entry should have 5 or 6 values", 0);
        }

        return new ScheduleImpl(
                parseSeconds(cronPieces.get(0)),
                parseMinutes(cronPieces.get(1)),
                parseHours(cronPieces.get(2)),
                parseDays(cronPieces.get(3)),
                parseMonths(cronPieces.get(4)),
                parseYears(cronPieces)
        );
    }

    private List<String> splitToPieces(CharSequence cronString) {
        return Arrays.asList(cronString.toString().split(" ")).stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private boolean isNumberOfPiecesCorrect(List<String> cronPieces) {
        return cronPieces.size() == 5 || hasYearField(cronPieces);
    }

    private boolean hasYearField(List<String> cronPieces) {
        return cronPieces.size() == 6;
    }

    private boolean isAny(String value) {
        return ANY.equals(value);
    }

    private static boolean notFirstAndNotLastChar(String str, String ch) {
        return (str.indexOf(ch) > 0) && (str.indexOf(ch) < str.length() - 1);
    }

    private boolean isRange(String rangeCandidate) {
        return rangeCandidate.contains(RANGE) &&
                notFirstAndNotLastChar(rangeCandidate, RANGE);
    }

    private List<Integer> parseRange(String range, Integer min, Integer max) throws ParseException {
        List<Integer> startAndEnd = stringToIntegerList(range, RANGE);
        if (startAndEnd.size() != 2) {
            throw new ParseException(String.format("Range %s should contain two values separated by %s", range, RANGE), 0);
        }
        Integer start = startAndEnd.get(0);
        if (start < min) {
            throw new ParseException(String.format("Range start value %d should be greater than or equal to %d", start, min), 0);
        }

        Integer end = startAndEnd.get(1);
        if (end > max) {
            throw new ParseException(String.format("Range end value %d should be less than or equal to %d", end, max), 0);
        }

        if (start > end) {
            throw new ParseException(String.format("Range start value %d should be less than or equal to %d", start, end), 0);
        }

        return createRange(start, end);
    }

    private List<Integer> createRange(Integer from, Integer to) {
        return IntStream
                .rangeClosed(from, to)
                .boxed()
                .collect(Collectors.toList());
    }

    private boolean isEnumeration(String enumerationCandidate) {
        return enumerationCandidate.contains(ENUMERATION) &&
                notFirstAndNotLastChar(enumerationCandidate, ENUMERATION);
    }

    private List<Integer> parseEnumeration(String enumeration, Integer min, Integer max) throws ParseException {
        List<Integer> ret = stringToIntegerList(enumeration, ENUMERATION);
        for (Integer value : ret) {
            if (value < min || value > max) {
                throw new ParseException(String.format("Enumeration value should be between %d and %d but %d is present", min, max, value), 0);
            }
        }
        return ret;
    }


    private boolean isIncrement(String incrementCandidate) {
        return incrementCandidate.contains(INCREMENT) &&
                notFirstAndNotLastChar(incrementCandidate, INCREMENT);
    }

    private List<Integer> parseIncrement(String increment, Integer min, Integer max) throws ParseException {
        List<String> startAndIncrement = Arrays.asList(increment.split(INCREMENT));
        if (startAndIncrement.size() != 2) {
            throw new ParseException(String.format("Increment specification %s should contain two values separated by %s", increment, INCREMENT), 0);
        }

        Integer start = (isAny(startAndIncrement.get(0))) ? min : Integer.parseInt(startAndIncrement.get(0));

        if (start < min) {
            throw new ParseException(String.format("Minimum value should be %d but is %d", min, start), 0);
        }

        Integer inc = Integer.parseInt(startAndIncrement.get(1));
        if (inc <= 0) {
            throw new ParseException(String.format("Increment should be greater than or equal to zero but %d was given", inc), 0);
        }

        //TODO: possibly convert to takeWhile
        Integer currentValue = start;
        List<Integer> ret = new ArrayList<>();
        while (currentValue <= max) {
            ret.add(currentValue);
            currentValue += inc;
        }

        return ret;
    }

    private List<Integer> parseInteger(String integerAsString, Integer min, Integer max) throws ParseException {
        Integer integer = Integer.parseInt(integerAsString);

        if (integer < min) {
            throw new ParseException(String.format("Minimum value should be %d but is %d", min, integer), 0);
        }

        if (integer > max) {
            throw new ParseException(String.format("Maximum value should be %d but is %d", max, integer), 0);
        }

        return Arrays.asList(integer);
    }

    private List<Integer> stringToIntegerList(String str, String separator) {
        return Arrays.asList(str.split(separator)).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<Integer> parseSeconds(String seconds) throws ParseException {
        final Integer MIN_SECONDS = 0;
        final Integer MAX_SECONDS = 59;
        if (isAny(seconds)) {
            return Collections.emptyList();
        }
        if (isRange(seconds)) {
            return parseRange(seconds, MIN_SECONDS, MAX_SECONDS);
        }
        if (isEnumeration(seconds)) {
            return parseEnumeration(seconds, MIN_SECONDS, MAX_SECONDS);
        }
        if (isIncrement(seconds)) {
            return parseIncrement(seconds, MIN_SECONDS, MAX_SECONDS);
        }
        try {
            return parseInteger(seconds, MIN_SECONDS, MAX_SECONDS);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Invalid seconds specification %s", seconds), 0);
        }
    }


    private List<Integer> parseMinutes(String minutes) throws ParseException {
        final Integer MIN_MINUTES = 0;
        final Integer MAX_MINUTES = 59;
        if (isAny(minutes)) {
            return Collections.emptyList();
        }
        if (isRange(minutes)) {
            return parseRange(minutes, MIN_MINUTES, MAX_MINUTES);
        }
        if (isEnumeration(minutes)) {
            return parseEnumeration(minutes, MIN_MINUTES, MAX_MINUTES);
        }
        if (isIncrement(minutes)) {
            return parseIncrement(minutes, MIN_MINUTES, MAX_MINUTES);
        }
        try {
            return parseInteger(minutes, MIN_MINUTES, MAX_MINUTES);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Invalid minutes specification %s", minutes), 0);
        }
    }

    private List<Integer> parseHours(String hours) throws ParseException {
        final Integer MIN_HOURS = 0;
        final Integer MAX_HOURS = 23;
        if (isAny(hours)) {
            return Collections.emptyList();
        }
        if (isRange(hours)) {
            return parseRange(hours, MIN_HOURS, MAX_HOURS);
        }
        if (isEnumeration(hours)) {
            return parseEnumeration(hours, MIN_HOURS, MAX_HOURS);
        }
        if (isIncrement(hours)) {
            return parseIncrement(hours, MIN_HOURS, MAX_HOURS);
        }
        try {
            return parseInteger(hours, MIN_HOURS, MAX_HOURS);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Invalid hours specification %s", hours), 0);
        }
    }

    private List<Integer> parseDays(String days) throws ParseException {
        final Integer MIN_DAYS = 1;
        final Integer MAX_DAYS = 31;
        if (isAny(days)) {
            return Collections.emptyList();
        }
        if (isRange(days)) {
            return parseRange(days, MIN_DAYS, MAX_DAYS);
        }
        if (isEnumeration(days)) {
            return parseEnumeration(days, MIN_DAYS, MAX_DAYS);
        }
        if (isIncrement(days)) {
            return parseIncrement(days, MIN_DAYS, MAX_DAYS);
        }
        try {
            return parseInteger(days, MIN_DAYS, MAX_DAYS);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Invalid days specification %s", days), 0);
        }
    }

    private List<Integer> parseMonths(String months) throws ParseException {
        final Integer MIN_MONTHS = 1;
        final Integer MAX_MONTHS = 12;
        if (isAny(months)) {
            return Collections.emptyList();
        }
        if (isRange(months)) {
            return parseRange(months, MIN_MONTHS, MAX_MONTHS);
        }
        if (isEnumeration(months)) {
            return parseEnumeration(months, MIN_MONTHS, MAX_MONTHS);
        }
        if (isIncrement(months)) {
            return parseIncrement(months, MIN_MONTHS, MAX_MONTHS);
        }
        try {
            return parseInteger(months, MIN_MONTHS, MAX_MONTHS);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Invalid months specification %s", months), 0);
        }
    }

    private List<Integer> parseYears(List<String> cronPieces) throws ParseException {
        if (hasYearField(cronPieces)) {
            final Integer MIN_YEARS = Integer.MIN_VALUE;
            final Integer MAX_YEARS = Integer.MAX_VALUE;
            String years = cronPieces.get(5);
            if (isAny(years)) {
                return Collections.emptyList();
            }
            if (isRange(years)) {
                return parseRange(years, MIN_YEARS, MAX_YEARS);
            }
            if (isEnumeration(years)) {
                return parseEnumeration(years, MIN_YEARS, MAX_YEARS);
            }
            try {
                return parseInteger(years, MIN_YEARS, MAX_YEARS);
            } catch (NumberFormatException e) {
                throw new ParseException(String.format("Invalid years specification %s", years), 0);
            }
        }
        return Collections.emptyList();
    }

}
