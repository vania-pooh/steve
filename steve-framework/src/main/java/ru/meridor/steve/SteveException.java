package ru.meridor.steve;

public class SteveException extends Exception {
    public SteveException(String message) {
        super(message);
    }

    public SteveException(String message, Throwable cause) {
        super(message, cause);
    }

    public SteveException(Throwable cause) {
        super(cause);
    }
}
