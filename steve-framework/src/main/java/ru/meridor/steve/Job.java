package ru.meridor.steve;

public interface Job<T, R> {

    R execute(T data) throws Exception;

}
