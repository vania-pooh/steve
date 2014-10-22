package ru.meridor.steve.job;

import ru.meridor.steve.annotations.Job;

import java.util.function.Function;

@Job
public class JobClass {
    
    @Job(async = true)
    public void asyncJob() {
        //No logic required
    }
    
    @Job
    public Function<String, Integer> syncJob() {
        return null;
    }
    
}
