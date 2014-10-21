package ru.meridor.steve.job;

import ru.meridor.steve.annotations.*;

import java.util.Optional;

@Job(async = true)
public class JobClass {

    private Integer someInteger;

    @Input
    public void inputMethod(Integer someInteger) {
        this.someInteger = someInteger;
    }

    @OnStart
    public void onStartMethod() {
        //Do nothing
    }

    @Output
    public String outputMethod() {
        return "some-string";
    }

    @OnFinish
    public void onFinishMethod() {
        //Do nothing
    }

    @OnInterrupt
    public void onInterruptMethod() {
        //Do nothing
    }

    public Integer getSomeInteger() {
        return someInteger;
    }
}
