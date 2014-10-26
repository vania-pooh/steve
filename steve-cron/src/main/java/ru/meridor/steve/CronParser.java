package ru.meridor.steve;

import java.text.ParseException;

public interface CronParser {

    Schedule parse(CharSequence cronString) throws ParseException;

}
