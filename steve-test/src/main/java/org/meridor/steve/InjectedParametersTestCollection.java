package org.meridor.steve;

import org.meridor.steve.annotations.JobCollection;

import java.util.function.Function;

@JobCollection(id = "injected-parameters-test-collection")
public class InjectedParametersTestCollection {

    @org.meridor.steve.annotations.Job(id = "injected-parameters-test-length")
    public static Function<String, Integer> testInjectedParametersLength(LengthCalculator lengthCalculator) {
        return lengthCalculator::calculate;
    }

    private InjectedParametersTestCollection() {
    }
}
