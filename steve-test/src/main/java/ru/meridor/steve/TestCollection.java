package ru.meridor.steve;

import ru.meridor.steve.annotations.Job;
import ru.meridor.steve.annotations.JobCollection;

import java.util.function.Function;

@JobCollection(id = "test-collection")
public class TestCollection {

    @Job(id = "test-length")
    public static Function<String, Integer> testLength() {
        return String::length;
    }

    private TestCollection(){}

}
