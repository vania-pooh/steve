package ru.meridor.steve;

public class LengthCalculatorImpl implements LengthCalculator {

    @Override
    public Integer calculate(String str) {
        return str.length();
    }
}
