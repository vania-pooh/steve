package ru.meridor.steve;

@ru.meridor.steve.annotations.Job(id = "injected-parameters-test-job-implementation")
public class InjectedParametersTestJobImplementation implements Job<String, Integer> {

    private final LengthCalculator lengthCalculator;

    public InjectedParametersTestJobImplementation(LengthCalculator lengthCalculator) {
        this.lengthCalculator = lengthCalculator;
    }

    @Override
    public Integer execute(String data) throws Exception {
        return lengthCalculator.calculate(data);
    }
}
