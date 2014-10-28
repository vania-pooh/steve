package ru.meridor.steve.job;

import ru.meridor.steve.annotations.Job;

@Job(id = "test-job")
public class JobClass {
    
    public void asyncJob() {
        //No logic required
    }
    
}
