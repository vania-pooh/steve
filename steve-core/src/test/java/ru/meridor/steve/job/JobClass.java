package ru.meridor.steve.job;

import ru.meridor.steve.annotations.*;

import java.util.Optional;
import java.util.function.Function;

@JobsContainer
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
