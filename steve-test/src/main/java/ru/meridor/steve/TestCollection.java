package ru.meridor.steve;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.meridor.steve.annotations.JobCollection;
import ru.meridor.steve.annotations.Job;

import java.util.function.Function;

@JobCollection(id = "test-collection")
public class TestCollection {

    @Job(id = "test-length")
    public static Function<String, Integer> testLength() {
        return String::length;
    }

    private TestCollection(){}

}
