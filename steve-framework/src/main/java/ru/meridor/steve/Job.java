package ru.meridor.steve;

import java.io.Serializable;

public interface Job<T extends Serializable, R extends Serializable> {

    R execute(T data) throws Exception;

}
