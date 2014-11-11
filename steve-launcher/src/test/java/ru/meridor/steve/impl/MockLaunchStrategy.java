package ru.meridor.steve.impl;

import ru.meridor.steve.JobSignature;
import ru.meridor.steve.LaunchStrategy;

public class MockLaunchStrategy implements LaunchStrategy {

    @Override
    public void launch(JobSignature jobSignature, Object data) {
        System.out.println("Mock!");
    }

}
