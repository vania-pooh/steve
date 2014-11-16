package ru.meridor.steve.job;

import ru.meridor.steve.Job;
import ru.meridor.steve.SteveException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

//TODO: convert this to a set of MethodProcessors
@Deprecated
public class Jobs {

//    public static <T, R> Job<T, R> fromFunction(final Function<T, R> function) {
//        return function::apply;
//    }
//
//    public static Job<Void, Void> fromRunnable(final Runnable runnable) {
//        return data -> {
//            runnable.run();
//            return null;
//        };
//    }
//
//    public static <T> Job<Void, T> fromCallable(final Callable<T> callable) {
//        return data -> callable.call();
//    }
//
//    public static <T> Job<T, Boolean> fromPredicate(final Predicate<T> predicate) {
//        return predicate::test;
//    }
//
//    public static <T> Job<Void, T> fromSupplier(final Supplier<T> supplier) {
//        return data -> supplier.get();
//    }
//
//    public static <T, R> Job<T, R> fromJob(Job<T, R> job) {
//        return job::execute;
//    }
//
//    private Jobs(){}

}
