package com.ltsllc.mirandaweb;

/**
 * Created by Clark on 6/3/2017.
 */
public class ShutdownException extends RuntimeException {
    public ShutdownException (String message) {
        super(message);
    }
}
