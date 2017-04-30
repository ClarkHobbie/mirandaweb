package com.ltsllc.mirandaweb.property;


/**
 * Created by Clark on 3/30/2017.
 */
public class UndefinedPropertyException extends Exception {
    private String name;

    public String getName() {
        return name;
    }

    public UndefinedPropertyException (String name) {
        this.name = name;
    }
}
