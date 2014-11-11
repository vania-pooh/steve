package ru.meridor.steve;

import org.apache.camel.Body;

//TODO: remove this bean
public class OutputBean {

    public void output(@Body String body) {
        System.out.println("Result = " + body);
    }

}
