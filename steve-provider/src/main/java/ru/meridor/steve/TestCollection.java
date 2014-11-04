package ru.meridor.steve;

import ru.meridor.steve.annotations.JobCollection;

import java.util.function.Function;

@JobCollection
public class TestCollection {

    public static Function<String, Integer> testLength() {
        return String::length;
    }

    private TestCollection(){}


    //По данному классу плагином генерируется XML, который используется на стороне запуска функций. В XML лежат имена job,
    // входные и выходные аргументы.
}
