package org.meridor.steve;

@org.meridor.steve.annotations.Job(id = "test-job-implementation")
public class TestJobImplementation implements Job<String, Integer> {

    @Override
    public Integer execute(String data) throws Exception {
        return data.length();
    }
}
