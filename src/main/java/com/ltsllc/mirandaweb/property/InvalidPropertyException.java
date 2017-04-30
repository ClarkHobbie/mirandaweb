package com.ltsllc.mirandaweb.property;

/**
 * Created by Clark on 3/30/2017.
 */
public class InvalidPropertyException extends Exception {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public InvalidPropertyException(Throwable cause, String name, String value) {
        super("Invalid property", cause);
        this.name = name;
        this.value = value;
    }
}
