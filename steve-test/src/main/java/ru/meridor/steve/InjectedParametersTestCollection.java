package ru.meridor.steve;

import ru.meridor.steve.annotations.Job;
import ru.meridor.steve.annotations.JobCollection;

import java.util.function.Function;

@JobCollection(id = "injected-parameters-test-collection")
public class InjectedParametersTestCollection {

    @Job(id = "injected-parameters-test-length")
    public static Function<String, Integer> testInjectedParametersLength(LengthCalculator lengthCalculator) {
        return lengthCalculator::calculate;
    }

    private InjectedParametersTestCollection(){}
}
