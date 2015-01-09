package org.meridor.steve;

import org.meridor.steve.annotations.JobCollection;

import java.util.function.Function;

@JobCollection(id = "test-collection")
public class TestCollection {

    @org.meridor.steve.annotations.Job(id = "test-length")
    public static Function<String, Integer> testLength() {
        return String::length;
    }

    private TestCollection() {
    }

}
