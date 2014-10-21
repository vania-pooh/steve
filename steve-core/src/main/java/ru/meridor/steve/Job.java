package ru.meridor.steve;

import ru.meridor.steve.job.Schedule;
import ru.meridor.steve.job.Trigger;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Job<T, R> extends Serializable {

//    default String getId() {
//        return UUID.randomUUID().toString();
//    }
//
//    default String getName() {
//        return getClass().getCanonicalName();
//    }

    R execute(T data) throws Exception;

//    default Optional<Schedule> getSchedule() {
//        return Optional.empty();
//    }
//
//    default List<Trigger> getTriggers() {
//        return Collections.emptyList();
//    }

}
