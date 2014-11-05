package ru.meridor.steve;

public class TestJobImplementation implements Job<String, Integer> {

    @Override
    public Integer execute(String data) throws Exception {
        return data.length();
    }
}
