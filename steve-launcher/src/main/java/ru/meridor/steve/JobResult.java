package ru.meridor.steve;

public interface JobResult<T> {

    Class<T> getType();

    T getData();

}
