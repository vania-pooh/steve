package ru.meridor.steve.job;

import ru.meridor.steve.annotations.Job;

public class JobClass {

    @Job(id = "async-job")
    public void asyncJob() {
        //No logic required
    }
    
}
