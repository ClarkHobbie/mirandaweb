package com.ltsllc.mirandaweb;

import com.ltsllc.mirandaweb.util.PropertiesUtils;
import com.ltsllc.mirandaweb.util.PropertyDelegate;

import java.util.Properties;

/**
 * Created by Clark on 4/30/2017.
 */
public class MirandaWebProperties extends PropertyDelegate {
    public static final String BASE_PACKAGE = "com.ltsllc.mirandaweb.";
    public static final String PROPERTY_BASE = BASE_PACKAGE + "base";
    public static final String PROPERTY_KEYSTORE = BASE_PACKAGE + "keystore";
    public static final String PROPERTY_PASSWORD = BASE_PACKAGE + "password";
    public static final String PROPERTY_PORT = BASE_PACKAGE + "port";

    public static final String DEFAULT_BASE = "html";
    public static final String DEFAULT_KEYSTORE = "keystore";
    public static final String DEFAULT_PORT = "443";

    public static final String[][] defaults = {
            { PROPERTY_BASE, DEFAULT_BASE },
            { PROPERTY_KEYSTORE, DEFAULT_KEYSTORE },
            { PROPERTY_PORT, DEFAULT_PORT },
    };

    public MirandaWebProperties () {
        Properties properties = PropertiesUtils.buildFrom(defaults);
        setProperties(properties);
    }

    public Properties asProperties () {
        return getProperties();
    }
}
