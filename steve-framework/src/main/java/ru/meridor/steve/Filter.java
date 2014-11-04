package ru.meridor.steve;

public interface Filter<T> {

    boolean matches(T entity);

}
