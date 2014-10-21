package ru.meridor.steve.job;

import ru.meridor.steve.Job;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.function.Function;

public class JobsMainTest {
    
    public static void main(String[] args) throws IOException {

        Job<String, Integer> job = Jobs.fromFunction(String::length);

        Function<String, Integer> f = (Function<String, Integer> & Serializable) (String::length);
        
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream so = new ObjectOutputStream(bo);
        so.writeObject(job);
        so.flush();
        String serializedObject = bo.toString();
        
        System.out.println(serializedObject);
        
    }
    
}
